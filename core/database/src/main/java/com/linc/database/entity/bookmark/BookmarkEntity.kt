package com.linc.database.entity.bookmark

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookmarkEntity(
    @PrimaryKey
    val bookmarkId: String,
    val targetId: String
)