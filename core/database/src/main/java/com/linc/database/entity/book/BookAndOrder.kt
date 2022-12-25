package com.linc.database.entity.book

import androidx.room.*
import com.linc.database.entity.order.OrderEntity

data class BookAndOrder(
    @Embedded
    val book: BookEntity,
    @Relation(
        parentColumn = "bookId",
        entityColumn = "targetId"
    )
    val order: OrderEntity?
)