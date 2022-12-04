package com.linc.bookdetails

import com.linc.model.Book

data class BookUiState(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val authors: List<String> = emptyList(),
    val categories: List<String> = emptyList(),
    val averageRating: Float = 0f,
    val ratingsCount: Float = 0f,
    val pageCount: Int = 0,
    val publishedDate: String = "",
    val language: String = "",
    val publisher: String = "",
    val availableForSale: Boolean = false,
    val price: Double = 0.0,
    val currency: String = "",
    val webResourceUrl: String = "",
    val isLoading: Boolean = false
)

fun Book.toUiState() = BookUiState(
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
    webResourceUrl = webResourceUrl
)