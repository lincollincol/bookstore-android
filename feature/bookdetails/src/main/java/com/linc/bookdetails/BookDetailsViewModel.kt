package com.linc.bookdetails

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.bookdetails.navigation.BookDetailsArgs
import com.linc.bookdetails.navigation.BookDetailsNavigationState
import com.linc.data.repository.BookmarksRepository
import com.linc.data.repository.BooksRepository
import com.linc.data.repository.OrdersRepository
import com.linc.model.Book
import com.linc.model.mockBooks
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import com.linc.ui.state.UiStateHolder
import com.linc.ui.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    defaultRouteNavigator: DefaultRouteNavigator,
    private val resourceProvider: ResourceProvider,
    private val booksRepository: BooksRepository,
    private val ordersRepository: OrdersRepository,
    private val bookmarksRepository: BookmarksRepository,
) : ViewModel(), UiStateHolder<BookUiState>, RouteNavigator by defaultRouteNavigator {

    private val bookDetailsArgs: BookDetailsArgs = BookDetailsArgs(savedStateHandle)

    override val uiState: StateFlow<BookUiState> = combine(
        booksRepository.getBookStream(bookDetailsArgs.bookId),
        ordersRepository.getBookOrderStream(bookDetailsArgs.bookId),
        bookmarksRepository.getBookmarkedBookStream(bookDetailsArgs.bookId)
    ) { book, order, bookmark ->
        book.toUiState(
            resourceProvider = resourceProvider,
            isOrdered = order != null,
            isBookmarked = bookmark != null
        )
    }
        .onStart { booksRepository.fetchBook(bookDetailsArgs.bookId) }
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
                ordersRepository.orderBook(rawUiState.id)
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
}