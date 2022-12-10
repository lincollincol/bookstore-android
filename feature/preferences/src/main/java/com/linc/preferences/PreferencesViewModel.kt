package com.linc.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.common.coroutines.state.UiStateHolder
import com.linc.data.repository.SubjectsRepository
import com.linc.model.Subject
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.NavigationState
import com.linc.navigation.RouteNavigator
import com.linc.preferences.navigation.PreferenceNavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    subjectsRepository: SubjectsRepository
) : ViewModel(), UiStateHolder<PreferencesUiState>, RouteNavigator by defaultRouteNavigator {

    override val uiState: StateFlow<PreferencesUiState> = subjectsRepository.getPrimarySubjectsStream()
        .map { PreferencesUiState(it.map(Subject::toUiState)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            PreferencesUiState()
        )


    fun selectSubject(id: String) {

    }

    fun addNewSubject() {
        navigateTo(PreferenceNavigationState.SubjectsEditor)
    }

}

