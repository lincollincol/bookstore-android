package com.linc.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.books.navigation.BooksNavigationState
import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.repository.BooksRepository
import com.linc.model.Book
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    private val booksRepository: BooksRepository,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), RouteNavigator by defaultRouteNavigator {

    private val _searchState = MutableStateFlow(SearchFieldUiState())
    val searchState: StateFlow<SearchFieldUiState> = _searchState.asStateFlow()

    val newBooksUiState: StateFlow<BooksUiState> = newBooksUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BooksUiState.Loading
        )

    init {
        fetchBooks()
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            try {
                booksRepository.fetchBooks()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun newBooksUiState() : Flow<BooksUiState> {
        return merge(
            booksRepository.getBooksStream(listOf("music")),
            booksRepository.getBooksStream(listOf("friction")),
            booksRepository.getBooksStream(listOf("science")),
        )
            .onEach { println(Thread.currentThread().name) }
            .map {
                BooksUiState.Success(emptyList())
//                BooksUiState.Success(it.map(Book::toUiState))
            }
            .onStart { BooksUiState.Loading }
            .catch { BooksUiState.Error }
            .flowOn(ioDispatcher)
    }

    fun selectBook(bookId: String) {
        navigateTo(BooksNavigationState.BookDetails(bookId))
    }

    fun updateSearchQuery(query: String) {
        _searchState.update { it.copy(query = query) }
    }

}

data class SearchFieldUiState(
    val query: String = ""
)

sealed interface BooksUiState {
    data class Success(val books: List<BooksSectionItemUiState>) : BooksUiState
    object Loading : BooksUiState
    object Error : BooksUiState
}

data class BooksSectionItemUiState(
    val title: String,
    val books: List<BookItemUiState>
)

data class BookItemUiState(
    val id: String,
    val imageUrl: String,
    val price: Double,
    val averageRating: Float,
    val ratingsCount: Float,
    val title: String
)

fun Book.toUiState() = BookItemUiState(
    id = id,
    imageUrl = imageUrl,
    price = price,
    averageRating = averageRating,
    ratingsCount = ratingsCount,
    title = title
)