package com.linc.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun AuthRoute(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    AuthScreen()
}

@Composable
private fun AuthScreen(
    onNameChange: (String) -> Unit,
) {

}

@Composable
private fun UsernameSetup() {

}

@Composable
private fun InterestsSetup() {

}