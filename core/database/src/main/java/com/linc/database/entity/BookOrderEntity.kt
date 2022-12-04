package com.linc.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class BookOrderEntity(
    @PrimaryKey val orderId: String,
    @Embedded val book: BookEntity,
    val count: Int
) {
    @Ignore
    val finalPrice: Double = count * book.price
}