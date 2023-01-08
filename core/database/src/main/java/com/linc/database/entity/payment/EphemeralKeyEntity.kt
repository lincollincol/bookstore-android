package com.linc.database.entity.payment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EphemeralKeyEntity(
    @PrimaryKey
    val keyId: String,
    val secret: String,
    val expires: Long
)

val EphemeralKeyEntity.isExpired: Boolean get() = expires < System.currentTimeMillis()