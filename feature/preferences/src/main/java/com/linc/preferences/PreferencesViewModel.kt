package com.linc.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.data.repository.SubjectsRepository
import com.linc.model.Subject
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.NavigationState
import com.linc.navigation.RouteNavigator
import com.linc.preferences.navigation.PreferenceNavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    private val subjectsRepository: SubjectsRepository
) : ViewModel(), RouteNavigator by defaultRouteNavigator {

    private val _preferencesUiState = MutableStateFlow(PreferencesUiState())
    val preferencesUiState = _preferencesUiState.asStateFlow()

    init {
        getSubjects()
    }

    private fun getSubjects() {
        viewModelScope.launch {
            try {
                val subjects = subjectsRepository.getPrimarySubjects()
                    .map { it.toUiState() }
                _preferencesUiState.update { it.copy(subjects = subjects) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun selectSubject(id: String) {

    }

    fun addNewSubject() {
        navigateTo(PreferenceNavigationState.SubjectsEditor)
    }

}

data class PreferencesUiState(
    val subjects: List<SubjectItemUiState> = emptyList()
)

data class SubjectItemUiState(
    val id: String,
    val name: String
)

fun Subject.toUiState() = SubjectItemUiState(
    id = id,
    name = name
)