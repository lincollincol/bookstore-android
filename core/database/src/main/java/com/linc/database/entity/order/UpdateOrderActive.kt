package com.linc.database.entity.order

data class UpdateOrderActive(
    val orderId: String,
    val isActive: Boolean,
    val lastModifiedMillis: Long
)