package com.linc.preferences

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.flowlayout.FlowRow
import com.linc.designsystem.component.SimpleIcon
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.asIconWrapper
import com.linc.navigation.observeNavigation
import com.linc.preferences.navigation.PreferenceNavigationState

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun PreferencesRoute(
    viewModel: PreferencesViewModel = hiltViewModel(),
    navigateToSubjectsEditor: () -> Unit
) {
    val preferencesUiState by viewModel.preferencesUiState.collectAsStateWithLifecycle()
    viewModel.observeNavigation {
        when(it) {
            PreferenceNavigationState.SubjectsEditor -> navigateToSubjectsEditor()
        }
    }
    PreferencesScreen(
        preferencesUiState = preferencesUiState,
        onSubject = viewModel::selectSubject,
        onNew = viewModel::addNewSubject
    )
}

@Composable
internal fun PreferencesScreen(
    preferencesUiState: PreferencesUiState,
    onSubject: (String) -> Unit,
    onNew: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SubjectsComponent(preferencesUiState.subjects, onSubject, onNew)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsComponent(
    subjects: List<SubjectItemUiState>,
    onSubject: (String) -> Unit,
    onNew: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Primary subjects")
        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            subjects.forEach {
                AssistChip(
                    label = { Text(text = it.name) },
                    onClick = { onSubject(it.id) }
                )
            }
            AssistChip(
                onClick = onNew,
                leadingIcon = { SimpleIcon(icon = BookstoreIcons.Add.asIconWrapper()) },
                label = { Text("new") }
            )
        }
    }
}