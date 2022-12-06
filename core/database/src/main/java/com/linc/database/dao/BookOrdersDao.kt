package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.linc.database.entity.book.BookOrderEntity

@Dao
interface BookOrdersDao {

    @Query("SELECT * FROM BookOrderEntity")
    suspend fun getBookOrders(): List<BookOrderEntity>

    @Query("SELECT * FROM BookOrderEntity WHERE BookOrderEntity.bookId = :id")
    suspend fun getBookOrder(id: String): BookOrderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookOrder(order: BookOrderEntity)

}
