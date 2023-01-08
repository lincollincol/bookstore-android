package com.linc.bookdetails

import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.bookdetails.navigation.BookDetailsArgs
import com.linc.bookdetails.navigation.BookDetailsNavigationState
import com.linc.common.coroutines.extensions.TEXT_PLAIN_TYPE
import com.linc.data.repository.BookmarksRepository
import com.linc.data.repository.BooksRepository
import com.linc.data.repository.OrdersRepository
import com.linc.data.repository.PaymentsRepository
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import com.linc.ui.state.UiStateHolder
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    defaultRouteNavigator: DefaultRouteNavigator,
    private val booksRepository: BooksRepository,
    private val ordersRepository: OrdersRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val paymentsRepository: PaymentsRepository
) : ViewModel(), UiStateHolder<BookUiState>, RouteNavigator by defaultRouteNavigator {

    companion object {
        private const val MIN_ORDER_COUNT: Int = 1
    }

    private val bookDetailsArgs = BookDetailsArgs(savedStateHandle)

    private val paymentClientSecret = MutableStateFlow<String?>(null)

    private val orderCountState = MutableStateFlow(MIN_ORDER_COUNT)

    override val uiState: StateFlow<BookUiState> = combine(
        booksRepository.getBookStream(bookDetailsArgs.bookId),
        ordersRepository.getActiveBookOrderStream(bookDetailsArgs.bookId),
        bookmarksRepository.getBookBookmarkStream(bookDetailsArgs.bookId),
        orderCountState,
        paymentClientSecret
    ) { book, order, bookmark, orderCount, paymentClientSecret ->
        book?.toUiState(
            orderCount = orderCount,
            paymentClientSecret = paymentClientSecret,
            orderId = order?.id,
            isOrdered = order != null,
            isBookmarked = bookmark != null,
            isLoading = false
        ) ?: BookUiState(isBookExist = false, isLoading = false)
    }
        .onStart { fetchBooks() }
        .catch { it.printStackTrace() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BookUiState(isLoading = true)
        )

    private suspend fun fetchBooks() {
        try {
            booksRepository.fetchBook(bookDetailsArgs.bookId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            try {
                if(rawUiState.isOrdered) {
                    navigateTo(BookDetailsNavigationState.Cart)
                    return@launch
                }
                ordersRepository.orderBook(rawUiState.bookId, orderCountState.value)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun confirmPayment() {
        viewModelScope.launch {
            try {
                val orderIds = rawUiState.orderId?.let(::listOf) ?: return@launch
                val paymentSecret = with(paymentsRepository) {
                    createOrdersPayment(orderIds)
                    getOrdersPaymentClientSecret(orderIds)
                } ?: return@launch
                paymentClientSecret.update { paymentSecret }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun handlePaymentResult(result: PaymentSheetResult) {
        when(result) {
            PaymentSheetResult.Completed -> completeOrderPayment()
            else -> { /* ignored */ }
        }
    }

    fun onPaymentConfirmed() {
        paymentClientSecret.update { null }
    }

    private fun completeOrderPayment() {
        viewModelScope.launch {
            try {
                val orderIds = rawUiState.orderId?.let(::listOf) ?: return@launch
                ordersRepository.makeOrdersActive(orderIds, false)
                paymentsRepository.deleteOrdersPaymentIntent(orderIds)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun bookmarkBook() {
        viewModelScope.launch {
            try {
                when {
                    rawUiState.isBookmarked -> bookmarksRepository.deleteBookBookmark(rawUiState.bookId)
                    else -> bookmarksRepository.bookmarkBook(rawUiState.bookId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun shareBook() {
        val content = String.format(
            "%s\n\n%s/books/%s",
            rawUiState.title,
            BuildConfig.DEEPLINK_BASE_URL,
            rawUiState.bookId
        )
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = TEXT_PLAIN_TYPE
            putExtra(Intent.EXTRA_TEXT, content)
        }
        navigateTo(BookDetailsNavigationState.Chooser(intent))
    }

    fun increaseOrderCount() = orderCountState.update { it + 1 }

    fun decreaseOrderCount() = orderCountState.update { (it - 1).coerceAtLeast(MIN_ORDER_COUNT) }

}