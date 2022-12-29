package com.linc.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.bookmarks.navigation.BookmarksNavigationState
import com.linc.data.repository.BookmarksRepository
import com.linc.model.BookBookmark
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import com.linc.ui.state.UiStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    private val bookmarksRepository: BookmarksRepository
) : ViewModel(), UiStateHolder<BookmarksUiState>, RouteNavigator by defaultRouteNavigator {

    override val uiState: StateFlow<BookmarksUiState> = bookmarksRepository.getBookBookmarks()
        .map { bookmarks ->
            BookmarksUiState(bookmarks = bookmarks.map(BookBookmark::toUiState))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BookmarksUiState()
        )

    fun selectBookmark(bookmarkId: String) {
        val bookId = rawUiState.bookmarks.find { it.bookmarkId == bookmarkId }?.bookId ?: return
        navigateTo(BookmarksNavigationState.BookDetails(bookId))
    }

    fun deleteBookmark(bookmarkId: String) {
        viewModelScope.launch {
            try {
                bookmarksRepository.deleteBookmark(bookmarkId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}