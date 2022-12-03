package com.linc.bookdetails

import com.linc.model.Book

data class BookUiState(
    val id: String = "",
    val imageUrl: String = "",
    val price: String = "",
    val averageRating: Int = 0,
    val ratingsCount: Int = 0,
    val subtitle: String = "",
    val description: String = "",
    val author: String = "",
    val title: String = "",
    val url: String = "",
    val isLoading: Boolean = false,
    val onBuyClick: () -> Unit = {}
)

fun Book.toUiState(
    onBuyClick: () -> Unit
) = BookUiState(
    id = id,
    imageUrl = image,
    price = price,
    averageRating = averageRating,
    ratingsCount = ratingsCount,
    subtitle = subtitle,
    description = description,
    author = author,
    title = title,
    url = url,
    onBuyClick = onBuyClick
)