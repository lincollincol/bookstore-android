package com.linc.database.entity.payment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CustomerEntity(
    @PrimaryKey
    val customerId: String,
    val userId: String,
)