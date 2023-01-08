package com.linc.database.dao

import androidx.room.*
import com.linc.database.entity.book.OrderAndBook
import com.linc.database.entity.order.OrderEntity
import com.linc.database.entity.order.UpdateOrderActive
import com.linc.database.entity.subject.SubjectEntity
import com.linc.database.entity.subject.UpdateSubjectPrimary
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersDao {

    @Query("SELECT * FROM OrderEntity WHERE OrderEntity.targetId = :targetId AND isActive = :isActive")
    fun getOrderByTarget(
        targetId: String,
        isActive: Boolean
    ): Flow<OrderEntity?>

    @Query("SELECT * FROM OrderEntity WHERE OrderEntity.orderId = :id")
    fun getOrderById(id: String): OrderEntity?

    @Transaction
    @Query("SELECT * FROM OrderEntity WHERE OrderEntity.orderId = :orderId")
    fun getOrderAndBookById(orderId: String): OrderAndBook?

    @Transaction
    @Query("SELECT * FROM OrderEntity WHERE isActive = :isActive")
    fun getOrderAndBooks(isActive: Boolean): Flow<List<OrderAndBook>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Update(entity = OrderEntity::class)
    suspend fun updateOrderActive(activeUpdate: UpdateOrderActive)

    @Query("DELETE FROM OrderEntity WHERE OrderEntity.orderId = :orderId")
    suspend fun deleteOrder(orderId: String)

    @Query("DELETE FROM OrderEntity")
    suspend fun clearTable()

}
