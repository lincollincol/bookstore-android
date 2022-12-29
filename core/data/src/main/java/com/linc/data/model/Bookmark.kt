package com.linc.data.model

import com.linc.database.entity.book.BookmarkAndBook
import com.linc.database.entity.bookmark.BookmarkEntity
import com.linc.model.BookBookmark
import com.linc.model.Bookmark

fun BookmarkEntity.asExternalModel() = Bookmark(
    id = bookmarkId,
    targetId = targetId
)

fun BookmarkAndBook.asExternalModel() = BookBookmark(
    book = bookEntity.asExternalModel(),
    bookmark = bookmarkEntity.asExternalModel()
)