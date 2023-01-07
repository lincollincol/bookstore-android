package com.linc.auth

import com.linc.common.coroutines.extensions.EMPTY
import com.linc.ui.state.UiState

data class AuthUiState(
    val name: String = String.EMPTY,
    val interests: List<String> = emptyList(),
    val isLoading: Boolean = false
) : UiState

val AuthUiState.isDataFilled: Boolean get() = name.isNotEmpty() && interests.isNotEmpty()