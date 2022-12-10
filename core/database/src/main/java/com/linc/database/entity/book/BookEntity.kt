package com.linc.database.entity.book

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BookEntity(
    @PrimaryKey
    val bookId: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val authors: List<String>,
    val categories: List<String>,
    val averageRating: Float?,
    val ratingsCount: Float?,
    val pageCount: Int,
    val publishedDate: String,
    val language: String,
    val publisher: String,
    val availableForSale: Boolean,
    val price: Double,
    val currency: String,
    val webResourceUrl: String
)