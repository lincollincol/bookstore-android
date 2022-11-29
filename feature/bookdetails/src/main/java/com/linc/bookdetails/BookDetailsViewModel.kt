package com.linc.bookdetails

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.bookdetails.navigation.BookDetailsArgs
import com.linc.model.mockBooks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val bookDetailsArgs: BookDetailsArgs = BookDetailsArgs(savedStateHandle)
    val bookUiState: StateFlow<BookUiState> = bookUiStateStream()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            BookUiState.Loading
        )

    private fun bookUiStateStream(): Flow<BookUiState> {
        return flowOf(
            mockBooks.find { it.id == bookDetailsArgs.bookId }
                ?.let(BookUiState::Success)
                ?: BookUiState.Error
        )
    }
}