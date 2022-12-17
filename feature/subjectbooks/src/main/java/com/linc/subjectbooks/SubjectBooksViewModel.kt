package com.linc.subjectbooks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.linc.common.coroutines.state.UiStateHolder
import com.linc.data.repository.BooksRepository
import com.linc.data.repository.SubjectsRepository
import com.linc.model.Book
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import com.linc.subjectbooks.navigation.SubjectBooksArgs
import com.linc.subjectbooks.navigation.SubjectBooksNavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SubjectBooksViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    defaultRouteNavigator: DefaultRouteNavigator,
    booksRepository: BooksRepository,
    subjectsRepository: SubjectsRepository
) : ViewModel(), UiStateHolder<SubjectBooksUiState>, RouteNavigator by defaultRouteNavigator {

    private val subjectBooksArgs = SubjectBooksArgs(savedStateHandle)

    val booksUiState: Flow<PagingData<BookItemUiState>> = booksRepository.getSubjectBooksStream(
        subjectBooksArgs.subjectId
    )
        .map { it.map(Book::toUiState) }
        .cachedIn(viewModelScope)

    private val firstBookUiState = MutableStateFlow(FirstPagingBookUiState())
    override val uiState: StateFlow<SubjectBooksUiState> = combine(
        firstBookUiState,
        subjectsRepository.getSubjectStream(subjectBooksArgs.subjectId)
    ) { firstBookState, subject ->
        SubjectBooksUiState(title = subject.name, firstBookState = firstBookState)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            SubjectBooksUiState()
        )

    fun selectBook(bookId: String) {
        navigateTo(SubjectBooksNavigationState.BookDetails(bookId))
    }

    fun updateFirstBookPosition(index: Int, offset: Int) {
        firstBookUiState.update { it.copy(index = index, offset = offset) }
//        println("$index $offset")
    }

}