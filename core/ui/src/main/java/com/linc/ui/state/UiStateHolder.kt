package com.linc.ui.state

import kotlinx.coroutines.flow.StateFlow

interface UiStateHolder<S : UiState> {
    val uiState: StateFlow<S>
    val UiStateHolder<S>.rawUiState: S get() = uiState.value
}
