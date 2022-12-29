package com.linc.preferences

import androidx.annotation.StringRes
import com.linc.ui.state.UiState
import com.linc.model.Subject
import com.linc.ui.icon.BookstoreIcons
import com.linc.ui.icon.IconWrapper
import com.linc.ui.icon.asIconWrapper

data class PreferencesUiState(
    val options: List<OptionItemUiState> = OptionItemUiState.values().asList()
) : UiState

data class SubjectItemUiState(
    val id: String,
    val name: String
) : UiState

enum class OptionItemUiState(
    val icon: IconWrapper,
    @StringRes val title: Int
) {
    Bookmarks(
        BookstoreIcons.OutlinedBookmark.asIconWrapper(),
        com.linc.ui.R.string.bookmarks
    ),
    Interests(
        BookstoreIcons.Interests.asIconWrapper(),
        com.linc.ui.R.string.interests
    )
}

fun Subject.toUiState() = SubjectItemUiState(
    id = id,
    name = name
)