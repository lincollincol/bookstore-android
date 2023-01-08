package com.linc.database.entity.payment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PaymentIntentEntity(
    @PrimaryKey
    val paymentId: String,
    val orderId: String,
    val clientSecret: String,
)