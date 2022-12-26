package com.linc.data.model

import com.linc.database.entity.book.BookAndBookmark
import com.linc.database.entity.bookmark.BookmarkEntity
import com.linc.model.Bookmark

fun BookmarkEntity.asExternalModel() = Bookmark(
    id = bookmarkId,
    targetId = targetId
)