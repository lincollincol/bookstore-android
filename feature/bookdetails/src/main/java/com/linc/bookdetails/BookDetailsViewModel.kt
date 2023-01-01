package com.linc.bookdetails

import android.content.ClipData
import android.content.ContentResolver.MimeTypeInfo
import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.bookdetails.navigation.BookDetailsArgs
import com.linc.bookdetails.navigation.BookDetailsNavigationState
import com.linc.common.coroutines.extensions.TEXT_PLAIN_TYPE
import com.linc.data.repository.BookmarksRepository
import com.linc.data.repository.BooksRepository
import com.linc.data.repository.OrdersRepository
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import com.linc.ui.state.UiStateHolder
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
) : ViewModel(), UiStateHolder<BookUiState>, RouteNavigator by defaultRouteNavigator {

    companion object {
        private const val MIN_ORDER_COUNT: Int = 1
    }

    private val bookDetailsArgs = BookDetailsArgs(savedStateHandle)

    private val orderCountState = MutableStateFlow(MIN_ORDER_COUNT)

    override val uiState: StateFlow<BookUiState> = combine(
        booksRepository.getBookStream(bookDetailsArgs.bookId),
        ordersRepository.getBookOrderStream(bookDetailsArgs.bookId),
        bookmarksRepository.getBookBookmarkStream(bookDetailsArgs.bookId),
        orderCountState
    ) { book, order, bookmark, orderCount ->
        book?.toUiState(
            orderCount = orderCount,
            isOrdered = order != null,
            isBookmarked = bookmark != null,
            isLoading = false
        ) ?: BookUiState(isBookExist = false, isLoading = false)
    }
        .onStart {
            try {

                booksRepository.fetchBook(bookDetailsArgs.bookId)
            } catch (e: Exception) {}
        }
        .catch {
            it.printStackTrace()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BookUiState(isLoading = true)
        )

    fun addToCart() {
        viewModelScope.launch {
            try {
                if(rawUiState.isOrdered) {
                    navigateTo(BookDetailsNavigationState.Cart)
                    return@launch
                }
                ordersRepository.orderBook(rawUiState.id, orderCountState.value)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun bookmarkBook() {
        viewModelScope.launch {
            try {
                when {
                    rawUiState.isBookmarked -> bookmarksRepository.deleteBookBookmark(rawUiState.id)
                    else -> bookmarksRepository.bookmarkBook(rawUiState.id)
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
            BuildConfig.BASE_BACKEND_URL,
            rawUiState.id
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