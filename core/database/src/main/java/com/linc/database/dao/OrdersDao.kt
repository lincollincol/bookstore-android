package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.linc.database.entity.book.BookAndOrder
import com.linc.database.entity.order.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersDao {

    @Transaction
    @Query("SELECT * FROM BookEntity")
    suspend fun getBookOrders(): List<BookAndOrder>

    @Transaction
    @Query("SELECT * FROM OrderEntity WHERE OrderEntity.targetId = :targetId")
    fun getOrderByTarget(targetId: String): Flow<OrderEntity?>

    @Transaction
    @Query("SELECT * FROM BookEntity WHERE BookEntity.bookId = :id")
    fun getBookOrder(id: String): Flow<BookAndOrder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

}
