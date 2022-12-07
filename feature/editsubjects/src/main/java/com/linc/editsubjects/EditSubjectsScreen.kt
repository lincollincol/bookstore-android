package com.linc.editsubjects

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linc.designsystem.component.BookstoreTextField

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun EditSubjectsRoute(
    viewModel: EditSubjectsViewModel = hiltViewModel()
) {
    val editSubjectUiState by viewModel.editSubjectUiStateUiState
        .collectAsStateWithLifecycle()
    EditSubjectsScreen(editSubjectUiState)
}

@Composable
fun EditSubjectsScreen(
    editSubjectUiState: EditSubjectUiState
) {
    Column {

        BookstoreTextField(

        )
    }
}