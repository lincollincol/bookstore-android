package com.linc.interests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.ui.state.UiStateHolder
import com.linc.data.repository.SubjectsRepository
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestsViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    private val subjectsRepository: SubjectsRepository
) : ViewModel(), UiStateHolder<InterestsUiState>, RouteNavigator by defaultRouteNavigator {

    override val uiState: StateFlow<InterestsUiState> = combine(
        subjectsRepository.getPrimarySubjectsStream(),
        subjectsRepository.getNonPrimarySubjectsStream()
    ) { primary, available ->
        primary + available
    }.map { subjects ->
        InterestsUiState(sections = subjects.toUiState())
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        InterestsUiState()
    )

    fun selectSubject(subjectId: String) {
        val item = rawUiState.sections
            .flatMap(InterestSectionUiState::items)
            .find { it.id == subjectId } ?: return
        val canMakePrimary = !rawUiState.isMaxPrimarySubjects && !item.isPrimary
        val canDeletePrimary = !rawUiState.isMinPrimarySubjects && item.isPrimary
        if(!canMakePrimary && !canDeletePrimary) {
            return
        }
        makeSubjectPrimary(subjectId, !item.isPrimary)
    }

    private fun makeSubjectPrimary(id: String, isPrimary: Boolean) {
        viewModelScope.launch {
            subjectsRepository.updateSubjectPrimary(id, isPrimary)
        }
    }

}