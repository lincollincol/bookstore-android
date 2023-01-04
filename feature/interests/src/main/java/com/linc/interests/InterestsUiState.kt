package com.linc.interests

import com.linc.ui.state.UiState
import com.linc.model.Subject

private const val MAX_PRIMARY_SUBJECTS = 5
private const val MIN_PRIMARY_SUBJECTS = 1

data class InterestsUiState(
    val sections: List<InterestSectionUiState> = emptyList(),
) : UiState

data class InterestSectionUiState(
    val isPrimary: Boolean,
    val items: List<InterestItemUiState>
) : UiState

data class InterestItemUiState(
    val id: String,
    val name: String,
    val isPrimary: Boolean
) : UiState

val InterestsUiState.primarySubjectsCount: Int get() =
    sections.find(InterestSectionUiState::isPrimary)?.items?.count() ?: 0

val InterestsUiState.isMinPrimarySubjects: Boolean get() =
    primarySubjectsCount <= MIN_PRIMARY_SUBJECTS

val InterestsUiState.isMaxPrimarySubjects: Boolean get() =
    primarySubjectsCount >= MAX_PRIMARY_SUBJECTS

val InterestsUiState.formattedSubjectsCount: String get() =
     String.format("(%d/%d)", primarySubjectsCount, MAX_PRIMARY_SUBJECTS)

fun List<Subject>.toUiState(): List<InterestSectionUiState> = groupBy { it.isPrimary }
    .map { InterestSectionUiState(it.key, it.value.map(Subject::toUiState)) }

fun Subject.toUiState() = InterestItemUiState(
    id = id,
    name = name,
    isPrimary = isPrimary
)