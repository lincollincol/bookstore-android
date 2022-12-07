package com.linc.editsubjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.data.repository.SubjectsRepository
import com.linc.model.Subject
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.NavigationState
import com.linc.navigation.RouteNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditSubjectsViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    private val subjectsRepository: SubjectsRepository
) : ViewModel(), RouteNavigator by defaultRouteNavigator {

    private val _editSubjectUiStateUiState = MutableStateFlow(EditSubjectUiState())
    val editSubjectUiStateUiState = _editSubjectUiStateUiState.asStateFlow()

    init {
        getSubjects()
    }

    private fun getSubjects() {
        viewModelScope.launch {
            try {
//                val subjects = subjectsRepository.getNonPrimarySubjects()
//                    .map { it.toUiState() }
//                val primarySubjects = subjectsRepository.getPrimarySubjects()


                val (primarySubjects, availableSubjects) = awaitAll(
                    async { subjectsRepository.getPrimarySubjects() },
                    async { subjectsRepository.getNonPrimarySubjects() }
                ).map { it.map(Subject::toUiState) }


                _editSubjectUiStateUiState.update {
                    it.copy(
                        primarySubjects = primarySubjects,
                        availableSubjects = availableSubjects
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateCustomSubjectName(value: String) {
        _editSubjectUiStateUiState.update {
            it.copy(customSubjectName = value)
        }
    }

    fun selectSubject(id: String) {

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