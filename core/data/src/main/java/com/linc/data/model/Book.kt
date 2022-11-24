package com.linc.data.model

import com.linc.model.Book
import com.linc.network.model.BookApiModel

fun BookApiModel.asExternalModel() = Book(
    id = isbn13,
    image = image,
    price = price,
    subtitle = subtitle,
    title = title,
    url = url
)