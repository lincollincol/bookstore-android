package com.linc.database.entity.book

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity
data class BookOrderEntity(
    @PrimaryKey val orderId: String,
    @Embedded val book: BookEntity,
    val count: Int
) {
    @Ignore
    val finalPrice: Double = count * book.price
}