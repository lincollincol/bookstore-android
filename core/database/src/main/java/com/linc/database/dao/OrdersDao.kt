package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.linc.database.entity.book.OrderAndBook
import com.linc.database.entity.order.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersDao {

    @Query("SELECT * FROM OrderEntity WHERE OrderEntity.targetId = :targetId")
    fun getOrderByTarget(targetId: String): Flow<OrderEntity?>

    @Query("SELECT * FROM OrderEntity WHERE OrderEntity.orderId = :id")
    fun getOrderById(id: String): OrderEntity?

    @Transaction
    @Query("SELECT * FROM OrderEntity WHERE OrderEntity.orderId = :orderId")
    fun getOrderAndBookById(orderId: String): OrderAndBook?

    @Transaction
    @Query("SELECT * FROM OrderEntity")
    fun getOrderAndBooks(): Flow<List<OrderAndBook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("DELETE FROM OrderEntity WHERE OrderEntity.orderId = :orderId")
    suspend fun deleteOrder(orderId: String)

}
