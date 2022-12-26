package com.linc.model

data class Book(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val authors: List<String>,
    val categories: List<String>,
    val averageRating: Float,
    val ratingsCount: Float,
    val pageCount: Int,
    val publishedDate: String,
    val language: String,
    val publisher: String,
    val availableForSale: Boolean,
    val price: Double,
    val currency: String,
    val webResourceUrl: String
) {
    // TODO: refactor
    // https://books.google.com/books/publisher/content/images/frontcover/8CugDwAAQBAJ?fife=w480-h690
    val hiqImageUrl = "https://books.google.com/books/publisher/content/images/frontcover/$id?fife=w480-h690"
}
