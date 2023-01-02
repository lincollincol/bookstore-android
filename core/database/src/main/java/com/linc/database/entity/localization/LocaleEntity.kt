package com.linc.database.entity.localization

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocaleEntity(
    @PrimaryKey
    val code: String,
    val version: Int
)