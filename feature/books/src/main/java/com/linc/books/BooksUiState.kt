package com.linc.books

import com.linc.common.coroutines.state.UiState
import com.linc.model.Book
import com.linc.model.SubjectBooks

data class BooksUiState(
    val books: List<BooksSectionItemUiState> = emptyList(),
    val searchQuery: String = ""
) : UiState

data class BooksSectionItemUiState(
    val subjectId: String,
    val title: String,
    val books: List<BookItemUiState>
) : UiState

data class BookItemUiState(
    val id: String,
    val imageUrl: String,
    val price: Double,
    val averageRating: Float,
    val ratingsCount: Float,
    val title: String
) : UiState

fun SubjectBooks.toUiState() = BooksSectionItemUiState(
    subjectId = subject.id,
    title = subject.name,
    books = books.map(Book::toUiState)
)

fun Book.toUiState() = BookItemUiState(
    id = id,
    imageUrl = imageUrl,
    price = price,
    averageRating = averageRating,
    ratingsCount = ratingsCount,
    title = title
)