package com.linc.database.entity.order

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderEntity(
    @PrimaryKey
    val orderId: String,
    val targetId: String,
    val count: Int,
    val isActive: Boolean,
    val lastModifiedMillis: Long
)