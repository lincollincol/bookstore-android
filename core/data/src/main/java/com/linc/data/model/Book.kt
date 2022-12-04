package com.linc.data.model

import com.linc.database.entity.BookEntity
import com.linc.database.entity.BookOrderEntity
import com.linc.model.Book
import com.linc.model.Book2
import com.linc.model.BookOrder
import com.linc.network.model.BookApiModel2
import com.linc.network.model.book.BookApiModel
import com.linc.network.model.book.Saleability

/*fun BookApiModel2.asExternalModel() = Book(
    id = isbn13,
    image = image,
    price = price,
    subtitle = subtitle,
    title = title,
    url = url,
    ratingsCount = 0,
    averageRating = 0,
    author = "",
    description = ""
)*/


fun BookApiModel.asExternalModel() = Book(
    id = id,
    title = volumeInfo.title,
    description = volumeInfo.description,
    imageUrl = volumeInfo.imageLinks.thumbnail,
    authors = volumeInfo.authors,
    categories = volumeInfo.categories,
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


fun BookApiModel.asExternalModel2() = Book2(
    id = id,
    image = volumeInfo.imageLinks.thumbnail,
    price = saleInfo?.listPrice?.productPrice.toString(),
    subtitle = searchInfo?.textSnippet.orEmpty(),
    title = volumeInfo.title,
    url = "",
    ratingsCount = volumeInfo.ratingsCount,
    averageRating = volumeInfo.averageRating,
    author = volumeInfo.authors.joinToString(),
    description = volumeInfo.description
)

fun Book.asEntity() = BookEntity(
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