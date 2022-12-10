package com.linc.subjectbooks

import com.linc.common.coroutines.state.UiState
import com.linc.common.coroutines.state.UiStateHolder

data class SubjectBooksUiState(
    val subjectId: String
) : UiState