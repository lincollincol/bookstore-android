package com.linc.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.linc.auth.navigation.AuthNavigationState
import com.linc.designsystem.component.BookstoreTextField
import com.linc.navigation.observeNavigation

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun AuthRoute(
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToMain: () -> Unit,
    navigateToInterests: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.observeNavigation {
        when(it) {
            AuthNavigationState.Main -> navigateToMain()
            AuthNavigationState.Interests -> navigateToInterests()
        }
    }
    AuthScreen(
        name = uiState.name,
        interests = uiState.interests,
        isDataFilled = uiState.isDataFilled,
        isLoading = uiState.isLoading,
        onFinishClick = viewModel::finishAuth,
        onEditInterestsClick = viewModel::editInterests,
        onNameChange = viewModel::updateName
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AuthScreen(
    modifier: Modifier = Modifier,
    name: String,
    interests: List<String>,
    isDataFilled: Boolean,
    isLoading: Boolean,
    onFinishClick: () -> Unit,
    onEditInterestsClick: () -> Unit,
    onNameChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize()
            .then(modifier),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Bookstore user setup",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        NameInputField(
            name = name,
            onNameChange = onNameChange
        )
        InterestsInputGroup(
            interests = interests,
            onEditInterestsClick = onEditInterestsClick
        )
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            enabled = isDataFilled,
            onClick = {
                keyboardController?.hide()
                onFinishClick()
            }
        ) {
            AnimatedVisibility(visible = isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            }
            AnimatedVisibility(visible = !isLoading) {
                Text(text = "Finish")
            }
        }
    }
}

@Composable
fun NameInputField(
    modifier: Modifier = Modifier,
    name: String,
    onNameChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Text(
            text = "What's your name?",
            style = MaterialTheme.typography.titleLarge,
        )
        BookstoreTextField(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            value = name,
            hint = "Joe",
            onValueChange = onNameChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterestsInputGroup(
    modifier: Modifier = Modifier,
    interests: List<String>,
    onEditInterestsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Text(
            text = "What's your interests?",
            style = MaterialTheme.typography.titleLarge
        )
        FlowRow(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            mainAxisAlignment = MainAxisAlignment.Start,
            mainAxisSpacing = 8.dp
        ) {
            interests.forEach {
                AssistChip(
                    label = { Text(text = it) },
                    onClick = {}
                )
            }
            TextButton(onClick = onEditInterestsClick) {
                Text(text = "Edit")
            }
        }
    }
}