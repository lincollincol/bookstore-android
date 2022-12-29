package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.linc.database.entity.book.BookmarkAndBook
import com.linc.database.entity.bookmark.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarksDao {

    @Transaction
    @Query("SELECT * FROM BookmarkEntity")
    fun getBookBookmarks() : Flow<List<BookmarkAndBook>>

    @Query("SELECT * FROM BookmarkEntity WHERE BookmarkEntity.targetId = :targetId")
    fun getBookmarkByTarget(targetId: String) : Flow<BookmarkEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM BookmarkEntity WHERE BookmarkEntity.bookmarkId = :id")
    suspend fun deleteBookmark(id: String)

    @Query("DELETE FROM BookmarkEntity WHERE BookmarkEntity.targetId = :targetId")
    suspend fun deleteBookmarkByTarget(targetId: String)

}