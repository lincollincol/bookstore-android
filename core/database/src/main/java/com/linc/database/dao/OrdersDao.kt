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

    @Transaction
    @Query("SELECT * FROM OrderEntity WHERE OrderEntity.targetId = :targetId")
    fun getOrderByTarget(targetId: String): Flow<OrderEntity?>

    @Transaction
    @Query("SELECT * FROM OrderEntity")
    fun getOrderAndBooks(): Flow<List<OrderAndBook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

}
