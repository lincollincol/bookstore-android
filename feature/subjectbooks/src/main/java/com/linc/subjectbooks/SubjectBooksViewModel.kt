package com.linc.subjectbooks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.linc.common.coroutines.state.UiStateHolder
import com.linc.data.repository.BooksRepository
import com.linc.model.Book
import com.linc.subjectbooks.navigation.SubjectBooksArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SubjectBooksViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    booksRepository: BooksRepository
) : ViewModel() {

    private val subjectBooksArgs = SubjectBooksArgs(savedStateHandle)

    val booksUiState: Flow<PagingData<BookItemUiState>> = booksRepository.getSubjectBooksStream(
        subjectBooksArgs.subjectId
    )
        .map { it.map(Book::toUiState) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            PagingData.empty()
        )
}