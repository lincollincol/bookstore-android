package com.linc.editsubjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.repository.SubjectsRepository
import com.linc.model.Subject
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.NavigationState
import com.linc.navigation.RouteNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditSubjectsViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    private val subjectsRepository: SubjectsRepository,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), RouteNavigator by defaultRouteNavigator {

    private val _editSubjectUiStateUiState = MutableStateFlow(EditSubjectUiState())
    val editSubjectUiStateUiState = _editSubjectUiStateUiState.asStateFlow()

    val uiState: StateFlow<EditSubjectUiState> = combine(
        subjectsRepository.getPrimarySubjectsStream(),
        subjectsRepository.getNonPrimarySubjectsStream(),
    ) { primarySubjects, availableSubjects ->
        EditSubjectUiState(
            primarySubjects = primarySubjects.map(Subject::toUiState),
            availableSubjects = availableSubjects.map(Subject::toUiState)
        )
    }
        .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        EditSubjectUiState()
    )

    fun updateCustomSubjectName(value: String) {
        _editSubjectUiStateUiState.update {
            it.copy(customSubjectName = value)
        }
    }

    fun selectAvailableSubject(id: String) {
        makeSubjectPrimary(id, true)
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

private const val MAX_PRIMARY_SUBJECTS = 5

data class EditSubjectUiState(
    val primarySubjects: List<SubjectItemUiState> = emptyList(),
    val availableSubjects: List<SubjectItemUiState> = emptyList(),
    val customSubjectName: String = ""
)

val EditSubjectUiState.isMaxPrimarySubjects: Boolean get() =
    primarySubjects.count() >= MAX_PRIMARY_SUBJECTS

data class SubjectItemUiState(
    val id: String,
    val name: String,
    val isPrimary: Boolean
)

fun Subject.toUiState() = SubjectItemUiState(
    id = id,
    name = name,
    isPrimary = isPrimary
)