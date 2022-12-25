package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.linc.database.entity.book.BookAndBookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarksDao {

    @Transaction
    @Query("SELECT * FROM BookEntity")
    fun getBookBookmarks() : Flow<BookAndBookmark>

}