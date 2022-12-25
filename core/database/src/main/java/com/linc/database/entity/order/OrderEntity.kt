package com.linc.database.entity.order

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.linc.database.entity.book.BookEntity

@Entity
data class OrderEntity(
    @PrimaryKey
    val orderId: String,
    val targetId: String,
    val count: Int
)