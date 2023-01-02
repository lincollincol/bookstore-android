package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.linc.database.entity.localization.LocaleEntity
import com.linc.database.entity.localization.LocaleStringEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocaleDao {

    @Query("SELECT * FROM LocaleEntity")
    suspend fun getLocales(): List<LocaleEntity>

//    @Query("SELECT * FROM LocaleEntity")
//    suspend fun getLocaleStream(code: String): Flow<LocaleEntity?>

    @Query("SELECT * FROM LocaleEntity WHERE LocaleEntity.code = :code")
    suspend fun getLocaleByCode(code: String): LocaleEntity?

    @Query("SELECT * FROM LocaleStringEntity")
    fun getLocaleStringsStream(): Flow<List<LocaleStringEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocale(localeEntity: LocaleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocaleString(localeStringEntity: LocaleStringEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocaleStrings(strings: List<LocaleStringEntity>)

    @Query("DELETE FROM LocaleEntity")
    suspend fun deleteLocale()

    @Query("DELETE FROM LocaleStringEntity")
    suspend fun deleteLocaleStrings()

    @Transaction
    suspend fun deleteLocaleData() {
        deleteLocale()
        deleteLocaleStrings()
    }

}