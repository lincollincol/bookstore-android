package com.linc.bookstore

import com.linc.ui.state.UiState
import com.linc.ui.state.UiStateHolder
import java.util.Locale

data class MainUiState(
    val localeStrings: Map<String, String> = mapOf()
) : UiState