package com.linc.data.model

import com.linc.database.entity.book.BookAndOrder
import com.linc.database.entity.order.OrderEntity
import com.linc.model.Order

fun BookAndOrder.asExternalModel(): Order? {
    val order = order ?: return null
    val book = book ?: return null
    return Order(
        id = order.orderId,
        targetId = book.bookId,
        count = order.count
    )
}

fun OrderEntity.asExternalModel() = Order(
    id= orderId,
    targetId = targetId,
    count = count
)