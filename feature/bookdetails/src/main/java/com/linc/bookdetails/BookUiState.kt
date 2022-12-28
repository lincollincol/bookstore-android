package com.linc.bookdetails

import com.linc.common.coroutines.extensions.EMPTY
import com.linc.common.coroutines.extensions.replaceLast
import com.linc.model.Book
import com.linc.ui.extensions.PRICE_WITH_CURRENCY_FORMAT
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
    val availableForSale: Boolean = true,
    val price: Double = 0.0,
    val currency: String = String.EMPTY,
    val webResourceUrl: String = String.EMPTY,
    val orderCount: Int = 0,
    val isOrdered: Boolean = false,
    val isBookmarked: Boolean = false,
    val isLoading: Boolean = false,
    val isBookExist: Boolean = true,
) : UiState

val BookUiState.totalOrderPrice: Double get() = price * orderCount

val BookUiState.canOrderBook: Boolean get() = availableForSale && isBookExist

val BookUiState.formattedPrice: String get() =
    String.format(PRICE_WITH_CURRENCY_FORMAT, price, currency)

val BookUiState.formattedTotalOrderPrice: String get() =
    String.format(PRICE_WITH_CURRENCY_FORMAT, totalOrderPrice, currency)

fun Book.toUiState(
    resourceProvider: ResourceProvider,
    orderCount: Int,
    isOrdered: Boolean,
    isBookmarked: Boolean,
    isLoading: Boolean,
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
    orderCount = orderCount,
    isOrdered = isOrdered,
    isBookmarked = isBookmarked,
    isLoading = isLoading
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