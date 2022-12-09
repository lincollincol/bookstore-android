package com.linc.editsubjects

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.linc.designsystem.component.BookstoreTextField

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun EditSubjectsRoute(
    viewModel: EditSubjectsViewModel = hiltViewModel()
) {
    val editSubjectUiState by viewModel.editSubjectUiStateUiState
        .collectAsStateWithLifecycle()
    val primarySubjectsUiState by viewModel.primarySubjectsUiState.collectAsStateWithLifecycle()
    val availableSubjectsUiState by viewModel.availableSubjectsUiState.collectAsStateWithLifecycle()
    EditSubjectsScreen(
        editSubjectUiState = editSubjectUiState,
        primarySubjectsUiState = primarySubjectsUiState,
        availableSubjectsUiState = availableSubjectsUiState,
        onPrimarySubjectClick = viewModel::selectPrimarySubject,
        onAvailableSubjectClick = viewModel::selectAvailableSubject
    )
}

@Composable
fun EditSubjectsScreen(
    editSubjectUiState: EditSubjectUiState,
    primarySubjectsUiState: SubjectsUiState,
    availableSubjectsUiState: SubjectsUiState,
    onPrimarySubjectClick: (String) -> Unit,
    onAvailableSubjectClick: (String) -> Unit,
) {
    Column {
        BookstoreTextField(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            value = "",
            onValueChange = {}
        )
        SubjectsComponent(
            title = "Primary subjects",
            subjectsUiState = primarySubjectsUiState,
            onSubject = onPrimarySubjectClick
        )
        SubjectsComponent(
            title = "Available subjects",
            subjectsUiState = availableSubjectsUiState,
            onSubject = onAvailableSubjectClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsComponent(
    title: String,
    subjectsUiState: SubjectsUiState,
    onSubject: (String) -> Unit
) {
    if(subjectsUiState is SubjectsUiState.Loading) {
        CircularProgressIndicator()
    } else if(subjectsUiState is SubjectsUiState.Success) {
        Text(text = title)
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisAlignment = MainAxisAlignment.Center,
            mainAxisSpacing = 8.dp
        ) {
            subjectsUiState.subjects.forEach {
                AssistChip(
                    label = { Text(text = it.name) },
                    onClick = { onSubject(it.id) }
                )
            }
        }
    }
}