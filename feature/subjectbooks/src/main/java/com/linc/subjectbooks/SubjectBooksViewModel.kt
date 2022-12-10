package com.linc.subjectbooks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.common.coroutines.state.UiStateHolder
import com.linc.subjectbooks.navigation.SubjectBooksArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SubjectBooksViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel(), UiStateHolder<SubjectBooksUiState> {

    private val subjectBooksArgs = SubjectBooksArgs(savedStateHandle)

    override val uiState: StateFlow<SubjectBooksUiState> = flowOf(subjectBooksArgs.subjectId)
        .map(::SubjectBooksUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            SubjectBooksUiState(subjectBooksArgs.subjectId)
        )

}