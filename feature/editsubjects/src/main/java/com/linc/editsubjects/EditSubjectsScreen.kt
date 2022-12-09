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
import com.linc.model.Subject
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun EditSubjectsRoute(
    viewModel: EditSubjectsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    EditSubjectsScreen(
        primarySubjects = uiState.primarySubjects,
        availableSubjects = uiState.availableSubjects,
        onPrimarySubjectClick = viewModel::selectPrimarySubject,
        onAvailableSubjectClick = viewModel::selectAvailableSubject
    )
}

@Composable
fun EditSubjectsScreen(
    primarySubjects: List<SubjectItemUiState>,
    availableSubjects: List<SubjectItemUiState>,
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
            subjects = primarySubjects,
            onSubject = onPrimarySubjectClick
        )
        SubjectsComponent(
            title = "Available subjects",
            subjects = availableSubjects,
            onSubject = onAvailableSubjectClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsComponent(
    title: String,
    subjects: List<SubjectItemUiState>,
    onSubject: (String) -> Unit
) {
    Text(text = title)
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        mainAxisAlignment = MainAxisAlignment.Center,
        mainAxisSpacing = 8.dp
    ) {
        subjects.forEach {
            AssistChip(
                label = { Text(text = it.name) },
                onClick = { onSubject(it.id) }
            )
        }
    }
}