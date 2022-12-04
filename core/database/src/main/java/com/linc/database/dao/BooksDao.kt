package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.linc.database.entity.BookEntity

@Dao
interface BooksDao {

    @Query("SELECT * FROM BookEntity WHERE BookEntity.id = :id")
    suspend fun getBook(id: String): BookEntity?

}