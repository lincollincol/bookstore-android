package com.linc.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import com.linc.ui.state.UiStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator
) : ViewModel(), UiStateHolder<AuthUiState>, RouteNavigator by defaultRouteNavigator {

    override val uiState: StateFlow<AuthUiState> = flowOf(AuthUiState())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AuthUiState()
        )

}
