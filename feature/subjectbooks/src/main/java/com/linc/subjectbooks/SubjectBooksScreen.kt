package com.linc.subjectbooks

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linc.model.SubjectBooks

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SubjectBooksRoute(
    viewModel: SubjectBooksViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SubjectBooksScreen(uiState.subjectId)
}

@Composable
fun SubjectBooksScreen(
    subjectId: String
) {
    Text(text = subjectId)
}