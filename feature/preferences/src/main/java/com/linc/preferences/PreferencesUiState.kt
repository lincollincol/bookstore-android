package com.linc.preferences

import com.linc.common.coroutines.state.UiState
import com.linc.model.Subject

data class PreferencesUiState(
    val subjects: List<SubjectItemUiState> = emptyList()
) : UiState

data class SubjectItemUiState(
    val id: String,
    val name: String
) : UiState

fun Subject.toUiState() = SubjectItemUiState(
    id = id,
    name = name
)