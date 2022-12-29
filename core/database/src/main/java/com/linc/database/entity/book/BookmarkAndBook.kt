package com.linc.database.entity.book

import androidx.room.Embedded
import androidx.room.Relation
import com.linc.database.entity.bookmark.BookmarkEntity

data class BookmarkAndBook(
    @Embedded
    val bookmarkEntity: BookmarkEntity,
    @Relation(
        parentColumn = "targetId",
        entityColumn = "bookId"
    )
    val bookEntity: BookEntity

)