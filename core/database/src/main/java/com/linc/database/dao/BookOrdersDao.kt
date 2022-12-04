package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.linc.database.entity.BookOrderEntity

@Dao
interface BookOrdersDao {

    @Query("SELECT * FROM BookOrderEntity")
    suspend fun getBookOrders(): List<BookOrderEntity>

    @Query("SELECT * FROM BookOrderEntity WHERE BookOrderEntity.id = :id")
    suspend fun getBookOrder(id: String): BookOrderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookOrder(order: BookOrderEntity)

}
