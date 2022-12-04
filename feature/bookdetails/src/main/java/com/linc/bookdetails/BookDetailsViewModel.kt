package com.linc.bookdetails

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.bookdetails.navigation.BookDetailsArgs
import com.linc.data.repository.BooksRepository
import com.linc.data.repository.OrdersRepository
import com.linc.model.Book
import com.linc.model.mockBooks
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    defaultRouteNavigator: DefaultRouteNavigator,
    private val booksRepository: BooksRepository,
    private val ordersRepository: OrdersRepository,
) : ViewModel(), RouteNavigator by defaultRouteNavigator {

    private val bookDetailsArgs: BookDetailsArgs = BookDetailsArgs(savedStateHandle)
    private val _bookUiState = MutableStateFlow(BookUiState(isLoading = true))
    val bookUiState: StateFlow<BookUiState> = _bookUiState.asStateFlow()

    init {
        getBook()
    }

    private fun getBook() {
        viewModelScope.launch {
            try {
                _bookUiState.update { it.copy(isLoading = true) }
                val book = booksRepository.getBook(bookDetailsArgs.bookId)
                    ?: return@launch
//                val book = mockBooks.first()
                _bookUiState.update { book.toUiState() }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _bookUiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun addToCart(id: String) {
        viewModelScope.launch {
            try {
                ordersRepository.createOrder(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}