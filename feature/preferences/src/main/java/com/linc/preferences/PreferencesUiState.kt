package com.linc.preferences

import androidx.annotation.StringRes
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.linc.ui.state.UiState
import com.linc.model.Subject
import com.linc.ui.icon.BookstoreIcons
import com.linc.ui.theme.IconWrapper
import com.linc.ui.theme.icons
import com.linc.ui.theme.strings

data class PreferencesUiState(
    val options: List<OptionItemUiState> = OptionItemUiState.values().asList()
) : UiState

data class SubjectItemUiState(
    val id: String,
    val name: String
) : UiState

enum class OptionItemUiState(
    val icon: @Composable () -> IconWrapper,
    val title: @Composable () -> String
) {
    Bookmarks(
        icon = { MaterialTheme.icons.outlinedBookmark },
        title = { MaterialTheme.strings.bookmarks }
    ),
    Interests(
        icon = { MaterialTheme.icons.interests },
        title = { MaterialTheme.strings.interests }
    ),
    Language(
        icon = { MaterialTheme.icons.language },
        title = { MaterialTheme.strings.language }
    ),
    Payments(
        icon = { MaterialTheme.icons.payments },
        title = { "Payments" }
    ),
    Logout(
        icon = { MaterialTheme.icons.logout },
        title = { "Logout" }
    )
}