package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.linc.database.entity.payment.CustomerEntity
import com.linc.database.entity.payment.EphemeralKeyEntity
import com.linc.database.entity.payment.PaymentIntentEntity
import com.linc.database.entity.user.UserEntity

@Dao
interface PaymentsDao {

    @Query("SELECT * FROM CustomerEntity LIMIT 1")
    suspend fun getCustomer() : CustomerEntity?

    @Query("SELECT * FROM EphemeralKeyEntity LIMIT 1")
    suspend fun getEphemeralKey() : EphemeralKeyEntity?

    @Query("SELECT * FROM PaymentIntentEntity WHERE PaymentIntentEntity.paymentId = :id")
    suspend fun getPaymentIntentById(id: String) : PaymentIntentEntity?

    @Query("SELECT * FROM PaymentIntentEntity WHERE PaymentIntentEntity.orderId = :id")
    suspend fun getPaymentIntentByOrderId(id: String) : PaymentIntentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: CustomerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEphemeralKey(key: EphemeralKeyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaymentIntent(key: PaymentIntentEntity)

    @Query("DELETE FROM EphemeralKeyEntity")
    suspend fun deleteAllEphemeralKeys()

    @Query("DELETE FROM CustomerEntity")
    suspend fun deleteAllCustomers()

    @Query("DELETE FROM PaymentIntentEntity WHERE PaymentIntentEntity.orderId = :orderId")
    suspend fun deleteOrderPaymentIntent(orderId: String)

    @Query("DELETE FROM CustomerEntity")
    suspend fun clearCustomerTable()

    @Query("DELETE FROM EphemeralKeyEntity")
    suspend fun clearEphemeralKeyTable()

    @Query("DELETE FROM PaymentIntentEntity")
    suspend fun clearPaymentIntentTable()

    @Transaction
    suspend fun clearTable() {
        clearPaymentIntentTable()
        clearEphemeralKeyTable()
        clearCustomerTable()
    }

}