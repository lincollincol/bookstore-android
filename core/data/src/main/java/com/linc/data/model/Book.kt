package com.linc.data.model

import com.linc.common.coroutines.extensions.EMPTY
import com.linc.database.entity.book.BookAndOrder
import com.linc.database.entity.book.BookEntity
import com.linc.model.Book
import com.linc.model.BookOrder
import com.linc.network.model.book.BookApiModel
import com.linc.network.model.book.Saleability

private val BookApiModel.salePrice: Double get() =
    saleInfo?.let { it?.listPrice?.amount ?: it?.retailPrice?.amount } ?: 0.0

private val BookApiModel.salePriceCurrency: String get() =
    saleInfo?.let { it?.listPrice?.currencyCode ?: it?.retailPrice?.currencyCode } ?: String.EMPTY

fun BookApiModel.asEntity() = BookEntity(
    bookId = id,
    title = volumeInfo.title.orEmpty(),
    description = volumeInfo.description.orEmpty(),
    imageUrl = volumeInfo.imageLinks?.thumbnail.orEmpty(),
    authors = volumeInfo.authors ?: emptyList(),
    categories = volumeInfo.categories ?: emptyList(),
    averageRating = volumeInfo.averageRating?.toFloat(),
    ratingsCount = volumeInfo.ratingsCount?.toFloat(),
    pageCount = volumeInfo.pageCount,
    publishedDate = volumeInfo.publishedDate.orEmpty(),
    language = volumeInfo.language,
    publisher = volumeInfo.publisher.orEmpty(),
    availableForSale = saleInfo?.saleability == Saleability.FOR_SALE,
    price = salePrice,
    currency = salePriceCurrency,
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
    title = volumeInfo.title.orEmpty(),
    description = volumeInfo.description.orEmpty(),
    imageUrl = volumeInfo.imageLinks?.thumbnail.orEmpty(),
    authors = volumeInfo.authors ?: emptyList(),
    categories = volumeInfo.categories ?: emptyList(),
    averageRating = volumeInfo.averageRating?.toFloat() ?: 0f,
    ratingsCount = volumeInfo.ratingsCount?.toFloat() ?: 0f,
    pageCount = volumeInfo.pageCount,
    publishedDate = volumeInfo.publishedDate.orEmpty(),
    language = volumeInfo.language,
    publisher = volumeInfo.publisher.orEmpty(),
    availableForSale = saleInfo?.saleability == Saleability.FOR_SALE,
    price = salePrice,
    currency = salePriceCurrency,
    webResourceUrl = accessInfo?.webReaderLink.orEmpty()
)