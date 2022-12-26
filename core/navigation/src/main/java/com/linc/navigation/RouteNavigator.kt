package com.linc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * Navigator to use when initiating navigation from a ViewModel.
 */
interface RouteNavigator {
    fun onNavigated(state: NavigationState)
    fun navigateBack()
    fun navigateTo(state: NavigationState)

    val navigationState: StateFlow<NavigationState>
}

/**
 * Default route navigator implementation
 */
class DefaultRouteNavigator @Inject constructor() : RouteNavigator {

    override val navigationState: MutableStateFlow<NavigationState> =
        MutableStateFlow(NavigationState.Idle)

    override fun onNavigated(state: NavigationState) {
        // clear navigation state, if state is the current state:
        navigationState.compareAndSet(state, NavigationState.Idle)
    }

    override fun navigateBack() {
        navigationState.update { NavigationState.Back }

    }

    override fun navigateTo(state: NavigationState) {
        navigationState.update { state }
    }

}

