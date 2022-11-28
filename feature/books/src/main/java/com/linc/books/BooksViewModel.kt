package com.linc.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor() : ViewModel() {

    private val _searchState = MutableStateFlow(SearchFieldState())
    val searchState: StateFlow<SearchFieldState> = _searchState.asStateFlow()

    fun searchUiState(): Flow<SearchUiState> {
        return flowOf(SearchUiState.Loading)
    }

    fun updateSearchQuery(query: String) {
        _searchState.update { it.copy(query = query) }
    }

}

data class SearchFieldState(
    val query: String = ""
)

sealed class SearchUiState {
    var query: String = ""
    data class Success(val books: List<Book>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
    object Loading : SearchUiState()
}