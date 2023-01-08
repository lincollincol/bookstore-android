package com.linc.bookstore

import com.linc.common.coroutines.extensions.EMPTY
import com.linc.model.AuthState
import com.linc.model.User
import com.linc.ui.state.UiState
import com.linc.ui.state.UiStateHolder
import java.util.Locale

data class MainUiState(
    val authState: AuthState = AuthState.UNKNOWN,
    val localeStrings: Map<String, String> = mapOf(),
) : UiState

val MainUiState.isAuthorized: Boolean? get() = when(authState) {
    AuthState.UNKNOWN -> null
    AuthState.AUTHORIZED -> true
    AuthState.UNAUTHORIZED -> false
}
