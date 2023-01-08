package com.linc.data.model

import com.linc.database.entity.book.OrderAndBook
import com.linc.database.entity.order.OrderEntity
import com.linc.model.BookOrder
import com.linc.model.Order

fun OrderAndBook.asExternalModel() = BookOrder(
    book = book.asExternalModel(),
    order = order.asExternalModel()
)

fun OrderEntity.asExternalModel() = Order(
    id= orderId,
    targetId = targetId,
    count = count,
    lastModifiedMillis = lastModifiedMillis
)