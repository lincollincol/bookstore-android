package com.linc.database.entity.book

import androidx.room.*
import com.linc.database.entity.order.OrderEntity

data class OrderAndBook(
    @Embedded
    val order: OrderEntity,
    @Relation(
        parentColumn = "targetId",
        entityColumn = "bookId"
    )
    val book: BookEntity,
)