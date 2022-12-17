package com.linc.editsubjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.ui.state.UiState
import com.linc.ui.state.UiStateHolder
import com.linc.data.repository.SubjectsRepository
import com.linc.model.Subject
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditSubjectsViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    private val subjectsRepository: SubjectsRepository
) : ViewModel(), com.linc.ui.state.UiStateHolder<EditSubjectUiState>, RouteNavigator by defaultRouteNavigator {

    private val customSubjectNameState = MutableStateFlow("")

    override val uiState: StateFlow<EditSubjectUiState> = combine(
        subjectsRepository.getPrimarySubjectsStream(),
        subjectsRepository.getNonPrimarySubjectsStream(),
        customSubjectNameState
    ) { primarySubjects, availableSubjects, customSubjectName ->
        EditSubjectUiState(
            primarySubjects = primarySubjects.map(Subject::toUiState),
            availableSubjects = availableSubjects.map(Subject::toUiState),
            customSubjectName = customSubjectName
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            EditSubjectUiState()
        )

    fun updateCustomSubjectName(value: String) {
        customSubjectNameState.update { value }
    }

    fun selectAvailableSubject(id: String) {
        if(!rawUiState.isMaxPrimarySubjects) {
            makeSubjectPrimary(id, true)
        }
    }

    fun selectPrimarySubject(id: String) {
        makeSubjectPrimary(id, false)
    }

    private fun makeSubjectPrimary(id: String, isPrimary: Boolean) {
        viewModelScope.launch {
            subjectsRepository.updateSubjectPrimary(id, isPrimary)
        }
    }

}