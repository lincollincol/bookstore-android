package com.linc.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.books.navigation.BooksNavigationState
import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.common.coroutines.state.UiStateHolder
import com.linc.data.repository.BooksRepository
import com.linc.model.Book
import com.linc.model.SubjectBooks
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
) : ViewModel(), UiStateHolder<BooksUiState>, RouteNavigator by defaultRouteNavigator {

    private val searchUiState = MutableStateFlow("")

    override val uiState: StateFlow<BooksUiState> = combine(
        booksRepository.getPrimarySubjectsBooksStream(),
        searchUiState
    ) { subjectsBooks, searchQuery ->
        BooksUiState(
            books = subjectsBooks.map(SubjectBooks::toUiState),
            searchQuery = searchQuery
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BooksUiState()
        )

    init {
        fetchBooks()
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            try {
                booksRepository.fetchBooksBySubjects()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun selectBook(bookId: String) {
        navigateTo(BooksNavigationState.BookDetails(bookId))
    }

    fun updateSearchQuery(query: String) {
        searchUiState.update { query }
    }

}