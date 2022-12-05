package com.linc.data.model

import com.linc.database.entity.BookEntity
import com.linc.database.entity.BookOrderEntity
import com.linc.model.Book
import com.linc.model.Book2
import com.linc.model.BookOrder
import com.linc.network.model.BookApiModel2
import com.linc.network.model.book.BookApiModel
import com.linc.network.model.book.Saleability

fun BookApiModel.asEntity() = BookEntity(
    id = id,
    title = volumeInfo.title,
    description = volumeInfo.description,
    imageUrl = volumeInfo.imageLinks.thumbnail,
    authors = volumeInfo.authors,
    categories = volumeInfo.categories
        .map { it.split("/") }
        .flatten()
        .distinct()
        .map { it.lowercase().trim() },
    averageRating = volumeInfo.averageRating.toFloat(),
    ratingsCount = volumeInfo.ratingsCount.toFloat(),
    pageCount = volumeInfo.pageCount,
    publishedDate = volumeInfo.publishedDate,
    language = volumeInfo.language,
    publisher = volumeInfo.publisher,
    availableForSale = saleInfo?.saleability == Saleability.FOR_SALE,
    price = saleInfo?.listPrice?.amountInMicros ?: 0.0,
    currency = saleInfo?.country.orEmpty(),
    webResourceUrl = accessInfo.webReaderLink
)

fun BookEntity.asExternalModel() = Book(
    id = id,
    title = title,
    description = description,
    imageUrl = imageUrl,
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

fun BookOrderEntity.asExternalModel() = BookOrder(
    id = orderId,
    book = book.asExternalModel()
)