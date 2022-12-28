package com.linc.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.linc.books.navigation.BooksNavigationState
import com.linc.common.coroutines.extensions.EMPTY
import com.linc.ui.state.UiStateHolder
import com.linc.data.repository.BooksRepository
import com.linc.model.Book
import com.linc.model.SubjectBooks
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import com.linc.ui.model.DetailedBookItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    private val booksRepository: BooksRepository
) : ViewModel(), UiStateHolder<BooksUiState>, RouteNavigator by defaultRouteNavigator {

    private val searchUiState = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchBooksUiState: Flow<PagingData<DetailedBookItemUiState>> = searchUiState
        .flatMapLatest { booksRepository.getPagedQueryBooksStream(it) }
        .map { it.map(Book::toDetailedItemUiState) }
        .cachedIn(viewModelScope)

    override val uiState: StateFlow<BooksUiState> = combine(
        booksRepository.getPrimarySubjectsBooksStream(),
        searchUiState
    ) { subjectsBooks, searchQuery ->
        BooksUiState(
            books = subjectsBooks.map(SubjectBooks::toUiState),
            searchQuery = searchQuery
        )
    }
        .onStart { fetchBooks() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BooksUiState()
        )

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

    fun selectSubject(subjectId: String) {
        navigateTo(BooksNavigationState.SubjectBooks(subjectId))
    }

    fun clearSearchQuery() {
        if(rawUiState.isSearching) {
            searchUiState.update { String.EMPTY }
        }
    }

    fun updateSearchQuery(query: String) {
        searchUiState.update { query }
    }

}