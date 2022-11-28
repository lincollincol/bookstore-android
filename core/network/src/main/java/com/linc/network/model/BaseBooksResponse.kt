package com.linc.network.model


data class BaseBooksResponse(
    val books: List<BookApiModel2>,
    val error: String,
    val total: String
)