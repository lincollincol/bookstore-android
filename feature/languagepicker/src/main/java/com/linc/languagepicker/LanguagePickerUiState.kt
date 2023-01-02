package com.linc.languagepicker

import com.linc.common.coroutines.extensions.EMPTY
import com.linc.ui.extensions.flagEmoji
import com.linc.ui.state.UiState
import java.util.*

data class LanguagePickerUiState(
    val locale: String = String.EMPTY,
    val languages: List<LanguageItemUiState> = emptyList()
) : UiState

data class LanguageItemUiState(
    val flagCode: String,
    val name: String,
    val code: String,
    val isSelected: Boolean
) : UiState

fun Locale.toUiState(isSelected: Boolean) = LanguageItemUiState(
    flagCode = flagEmoji,
    name = displayName,
    code = language,
    isSelected = isSelected
)