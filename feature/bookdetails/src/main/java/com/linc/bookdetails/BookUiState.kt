package com.linc.bookdetails

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.linc.common.coroutines.extensions.EMPTY
import com.linc.common.coroutines.extensions.replaceLast
import com.linc.model.Book
import com.linc.ui.extensions.PRICE_WITH_CURRENCY_FORMAT
import com.linc.ui.state.UiState
import com.linc.ui.theme.strings


data class BookUiState(
    val id: String = String.EMPTY,
    val title: String = String.EMPTY,
    val description: String = String.EMPTY,
    val imageUrl: String = String.EMPTY,
    val authors: List<String> = emptyList(),
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
    val orderCount: Int = 0,
    val isOrdered: Boolean = false,
    val isBookmarked: Boolean = false,
    val isLoading: Boolean = false,
    val isBookExist: Boolean = true,
) : UiState

val BookUiState.totalOrderPrice: Double get() = price * orderCount

val BookUiState.canOrderBook: Boolean get() = availableForSale && isBookExist

val BookUiState.formattedPrice: String get() =
    PRICE_WITH_CURRENCY_FORMAT.format(price, currency)

val BookUiState.formattedTotalOrderPrice: String get() =
    PRICE_WITH_CURRENCY_FORMAT.format(totalOrderPrice, currency)

fun Book.toUiState(
    orderCount: Int,
    isOrdered: Boolean,
    isBookmarked: Boolean,
    isLoading: Boolean,
) = BookUiState(
    id = id,
    title = title,
    description = description,
    imageUrl = hiqImageUrl,
    authors = authors,
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

val BookUiState.formattedAuthors: String
    @Composable
    get() {
        if(authors.count() == 1) {
            return authors.first()
        }
        val separator = ", "
        return authors
            .joinToString(separator)
            .replaceLast(separator, MaterialTheme.strings.andSpaced)
    }