package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.linc.database.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Query("SELECT * FROM BookEntity WHERE BookEntity.id = :id")
    suspend fun getBook(id: String): BookEntity?

    @Query("SELECT * FROM BookEntity")
    suspend fun getBooks(): List<BookEntity>

    @Query("SELECT * FROM BookEntity")
    fun getBooksStream(): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)

}