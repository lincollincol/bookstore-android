package com.linc.bookdetails

import com.linc.common.coroutines.extensions.EMPTY
import com.linc.common.coroutines.extensions.replaceLast
import com.linc.model.Book
import com.linc.model.BookOrder
import com.linc.ui.state.UiState
import com.linc.ui.util.ResourceProvider

data class BookUiState(
    val id: String = String.EMPTY,
    val title: String = String.EMPTY,
    val description: String = String.EMPTY,
    val imageUrl: String = String.EMPTY,
    val authors: String = String.EMPTY,
    val categories: List<String> = emptyList(),
    val averageRating: Float = 0f,
    val ratingsCount: Float = 0f,
    val pageCount: Int = 0,
    val publishedDate: String = String.EMPTY,
    val language: String = String.EMPTY,
    val publisher: String = String.EMPTY,
    val availableForSale: Boolean = false,
    val price: Double = 0.0,
    val currency: String = String.EMPTY,
    val webResourceUrl: String = String.EMPTY,
    val isLoading: Boolean = false,
    val isOrdered: Boolean = false
) : UiState

fun Book.toUiState(
    resourceProvider: ResourceProvider,
    isOrdered: Boolean
) = BookUiState(
    id = id,
    title = title,
    description = description,
    imageUrl = hiqImageUrl,
    authors = formatAuthors(resourceProvider, authors),
    categories = categories,
    averageRating = averageRating,
    ratingsCount = ratingsCount,
    pageCount = pageCount,
    publishedDate = publishedDate,
    language = language,
    publisher = publisher,
    availableForSale = availableForSale,
    price = price,
    currency = currency,
    webResourceUrl = webResourceUrl,
    isOrdered = isOrdered
)

private fun formatAuthors(
    resourceProvider: ResourceProvider,
    authors: List<String>
): String {
    if(authors.count() == 1) {
        return authors.first()
    }
    return authors.joinToString()
        .replaceLast(", ", resourceProvider.getString(com.linc.ui.R.string.and_spaced))
}