package com.linc.ui.model

import com.linc.ui.extensions.PRICE_WITH_CURRENCY_FORMAT
import com.linc.ui.state.UiState

data class DetailedBookItemUiState(
    val id: String,
    val imageUrl: String,
    val authors: String,
    val price: Double,
    val currency: String,
    val averageRating: Float,
    val ratingsCount: Float,
    val title: String
) : UiState

val DetailedBookItemUiState.formattedPrice: String get() =
    PRICE_WITH_CURRENCY_FORMAT.format(price, currency)