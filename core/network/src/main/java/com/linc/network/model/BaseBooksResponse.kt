package com.linc.network.model


data class BaseBooksResponse(
    val books: List<BookApiModel>,
    val error: String,
    val total: String
)