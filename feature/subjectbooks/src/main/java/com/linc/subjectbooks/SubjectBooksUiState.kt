package com.linc.subjectbooks

import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.linc.ui.state.UiState
import com.linc.ui.state.UiStateHolder
import com.linc.model.Book
import com.linc.model.Subject
import com.linc.ui.model.DetailedBookItemUiState

data class SubjectBooksUiState(
    val title: String = "",
    val firstBookState: FirstPagingBookUiState = FirstPagingBookUiState()
) : UiState

data class FirstPagingBookUiState(
    val index: Int = 0,
    val offset: Int = 0
)

fun Book.toUiState() = DetailedBookItemUiState(
    id = id,
    imageUrl = imageUrl,
    price = price,
    currency = currency,
    averageRating = averageRating,
    ratingsCount = ratingsCount,
    title = title,
    authors = authors.joinToString()
)