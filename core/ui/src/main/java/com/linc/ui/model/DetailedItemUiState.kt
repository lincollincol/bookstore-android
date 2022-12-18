package com.linc.ui.model

import com.linc.ui.state.UiState

data class DetailedBookItemUiState(
    val id: String,
    val imageUrl: String,
    val authors: String,
    val price: Double,
    val averageRating: Float,
    val ratingsCount: Float,
    val title: String
) : UiState