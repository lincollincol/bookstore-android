package com.linc.preferences

import com.linc.ui.state.UiState
import com.linc.model.Subject

data class PreferencesUiState(
    val subjects: List<SubjectItemUiState> = emptyList()
) : com.linc.ui.state.UiState

data class SubjectItemUiState(
    val id: String,
    val name: String
) : com.linc.ui.state.UiState

fun Subject.toUiState() = SubjectItemUiState(
    id = id,
    name = name
)