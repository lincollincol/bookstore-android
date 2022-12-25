package com.linc.database.entity.book

import androidx.room.Embedded
import androidx.room.Relation
import com.linc.database.entity.bookmark.BookmarkEntity

data class BookAndBookmark(
    @Embedded
    val bookEntity: BookEntity,
    @Relation(
        parentColumn = "bookId",
        entityColumn = "targetId"
    )
    val bookmarkEntity: BookmarkEntity
)