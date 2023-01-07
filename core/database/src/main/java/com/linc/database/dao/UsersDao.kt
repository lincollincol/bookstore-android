package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.linc.database.entity.user.UserEntity

@Dao
interface UsersDao {

    @Query("SELECT * FROM UserEntity LIMIT 1")
    suspend fun getUser() : UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

}