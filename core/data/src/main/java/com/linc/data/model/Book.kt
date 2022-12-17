package com.linc.data.model

import com.linc.database.entity.book.BookEntity
import com.linc.database.entity.book.BookOrderEntity
import com.linc.model.Book
import com.linc.model.BookOrder
import com.linc.network.model.book.BookApiModel
import com.linc.network.model.book.Saleability

fun BookApiModel.asEntity() = BookEntity(
    bookId = id,
    title = volumeInfo.title.orEmpty(),
    description = volumeInfo?.description.orEmpty(),
    imageUrl = volumeInfo?.imageLinks?.thumbnail.orEmpty(),
    authors = volumeInfo?.authors ?: emptyList(),
    categories = volumeInfo?.categories ?: emptyList(),
    averageRating = volumeInfo?.averageRating?.toFloat(),
    ratingsCount = volumeInfo?.ratingsCount?.toFloat(),
    pageCount = volumeInfo?.pageCount ?: 0,
    publishedDate = volumeInfo?.publishedDate.orEmpty(),
    language = volumeInfo?.language.orEmpty(),
    publisher = volumeInfo?.publisher.orEmpty(),
    availableForSale = saleInfo?.saleability == Saleability.FOR_SALE,
    price = saleInfo?.listPrice?.amountInMicros ?: 0.0,
    currency = saleInfo?.country.orEmpty(),
    webResourceUrl = accessInfo?.webReaderLink.orEmpty()
)

fun BookEntity.asExternalModel() = Book(
    id = bookId,
    title = title,
    description = description,
    imageUrl = imageUrl,
    authors = authors,
    categories = categories,
    averageRating = averageRating ?: 0f,
    ratingsCount = ratingsCount ?: 0f,
    pageCount = pageCount,
    publishedDate = publishedDate,
    language = language,
    publisher = publisher,
    availableForSale = availableForSale,
    price = price,
    currency = currency,
    webResourceUrl = webResourceUrl
)

fun BookApiModel.asExternalModel() = Book(
    id = id,
    title = volumeInfo?.title.orEmpty(),
    description = volumeInfo?.description.orEmpty(),
    imageUrl = volumeInfo?.imageLinks?.thumbnail.orEmpty(),
    authors = volumeInfo?.authors ?: emptyList(),
    categories = volumeInfo?.categories ?: emptyList(),
    averageRating = volumeInfo?.averageRating?.toFloat() ?: 0f,
    ratingsCount = volumeInfo?.ratingsCount?.toFloat() ?: 0f,
    pageCount = volumeInfo?.pageCount ?: 0,
    publishedDate = volumeInfo?.publishedDate.orEmpty(),
    language = volumeInfo?.language.orEmpty(),
    publisher = volumeInfo?.publisher.orEmpty(),
    availableForSale = saleInfo?.saleability == Saleability.FOR_SALE,
    price = saleInfo?.listPrice?.amountInMicros ?: 0.0,
    currency = saleInfo?.country.orEmpty(),
    webResourceUrl = accessInfo?.webReaderLink.orEmpty()
)

fun BookOrderEntity.asExternalModel() = BookOrder(
    id = orderId,
    book = book.asExternalModel()
)