package com.linc.bookdetails

import com.linc.model.Book

sealed interface BookUiState {
    data class Success(val book: Book) : BookUiState
    object Error : BookUiState
    object Loading : BookUiState
}