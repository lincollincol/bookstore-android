package com.linc.data.model

import com.linc.database.entity.book.BookAndOrder
import com.linc.model.BookOrder

fun BookAndOrder.asExternalModel(): BookOrder? {
    val order = order ?: return null
    val book = book ?: return null
    return BookOrder(
        id = order.orderId,
        bookId = book.bookId,
        count = order.count
    )
}