package com.linc.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.books.navigation.BooksNavigationState
import com.linc.data.repository.BooksRepository
import com.linc.model.Book
import com.linc.model.mockBooks
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    private val booksRepository: BooksRepository
) : ViewModel(), RouteNavigator by defaultRouteNavigator {

    private val _searchState = MutableStateFlow(SearchFieldUiState())
    val searchState: StateFlow<SearchFieldUiState> = _searchState.asStateFlow()

    private val _booksUiState: MutableStateFlow<BooksUiState> = MutableStateFlow(BooksUiState())
    val booksUiState: StateFlow<BooksUiState> = _booksUiState.asStateFlow()

    init {
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch {
            try {
//                val books = booksRepository.getBooks()
                val books = mockBooks
                    .map {
                        it.toUiState { selectBook(it) }
                    }
                _booksUiState.update {
                    it.copy(
                        books,
                        books
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun selectBook(book: Book) {
        navigateTo(BooksNavigationState.NavigateToBook(book.id))
    }

    fun updateSearchQuery(query: String) {
        _searchState.update { it.copy(query = query) }
    }

}

data class SearchFieldUiState(
    val query: String = ""
)

data class BooksUiState(
    val newBooks: List<BookItemUiState> = emptyList(),
    val recommendedBooks: List<BookItemUiState> = emptyList()
)

data class BookItemUiState(
    val id: String,
    val imageUrl: String,
    val price: String,
    val averageRating: Int,
    val ratingsCount: Int,
    val subtitle: String,
    val author: String,
    val title: String,
    val url: String,
    val onClick: () -> Unit
)

fun Book.toUiState(
    onClick: () -> Unit
) = BookItemUiState(
    id = id,
    imageUrl = image,
    price = price,
    averageRating = averageRating,
    ratingsCount = ratingsCount,
    subtitle = subtitle,
    author = author,
    title = title,
    url = url,
    onClick = onClick
)