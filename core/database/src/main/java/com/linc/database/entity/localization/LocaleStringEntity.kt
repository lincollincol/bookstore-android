package com.linc.database.entity.localization

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocaleStringEntity(
    @PrimaryKey
    val key: String,
    val value: String,
    val localeId: String
)