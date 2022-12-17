package com.linc.network.model.book

data class VolumeInfo(
    val authors: List<String>?,
    val averageRating: Double?,
    val canonicalVolumeLink: String,
    val categories: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val infoLink: String?,
    val language: String,
    val pageCount: Int,
    val previewLink: String,
    val publishedDate: String?,
    val publisher: String?,
    val ratingsCount: Int?,
    val title: String?
)