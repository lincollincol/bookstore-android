package com.linc.subjectbooks

import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.linc.common.coroutines.state.UiState
import com.linc.common.coroutines.state.UiStateHolder
import com.linc.model.Book
import com.linc.model.Subject

data class SubjectBooksUiState(
    val title: String = "",
    val firstBookState: FirstPagingBookUiState = FirstPagingBookUiState()
) : UiState

data class FirstPagingBookUiState(
    val index: Int = 0,
    val offset: Int = 0
)

data class BookItemUiState(
    val id: String,
    val imageUrl: String,
    val authors: String,
    val price: Double,
    val averageRating: Float,
    val ratingsCount: Float,
    val title: String
) : UiState

fun Book.toUiState() = BookItemUiState(
    id = id,
    imageUrl = imageUrl,
    price = price,
    averageRating = averageRating,
    ratingsCount = ratingsCount,
    title = title,
    authors = authors.joinToString()
)