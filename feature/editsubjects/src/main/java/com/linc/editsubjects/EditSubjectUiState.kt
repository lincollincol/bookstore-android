package com.linc.editsubjects

import com.linc.common.coroutines.state.UiState
import com.linc.model.Subject

private const val MAX_PRIMARY_SUBJECTS = 5

data class EditSubjectUiState(
    val primarySubjects: List<SubjectItemUiState> = emptyList(),
    val availableSubjects: List<SubjectItemUiState> = emptyList(),
    val customSubjectName: String = ""
) : UiState

data class SubjectItemUiState(
    val id: String,
    val name: String,
    val isPrimary: Boolean
) : UiState

val EditSubjectUiState.isMaxPrimarySubjects: Boolean get() =
    primarySubjects.count() >= MAX_PRIMARY_SUBJECTS

fun Subject.toUiState() = SubjectItemUiState(
    id = id,
    name = name,
    isPrimary = isPrimary
)