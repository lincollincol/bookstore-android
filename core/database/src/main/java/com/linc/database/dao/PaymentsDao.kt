package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.linc.database.entity.payment.CustomerEntity
import com.linc.database.entity.user.UserEntity

@Dao
interface PaymentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: CustomerEntity)

}