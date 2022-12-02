package com.linc.data.model

import com.linc.model.Book
import com.linc.network.model.BookApiModel2
import com.linc.network.model.book.BookApiModel

fun BookApiModel2.asExternalModel() = Book(
    id = isbn13,
    image = image,
    price = price,
    subtitle = subtitle,
    title = title,
    url = url,
    ratingsCount = 0,
    averageRating = 0,
    author = ""
)

fun BookApiModel.asExternalModel() = Book(
    id = id,
    image = volumeInfo.imageLinks.thumbnail,
    price = saleInfo?.listPrice?.productPrice.toString(),
    subtitle = searchInfo.textSnippet,
    title = volumeInfo.title,
    url = "",
    ratingsCount = volumeInfo.ratingsCount,
    averageRating = volumeInfo.averageRating,
    author = volumeInfo.authors.joinToString()
)