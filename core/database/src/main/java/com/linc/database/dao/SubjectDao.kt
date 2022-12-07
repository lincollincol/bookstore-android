package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.linc.database.entity.subject.SubjectEntity
import com.linc.database.entity.subject.SubjectWithBooks

@Dao
interface SubjectDao {

    @Query("SELECT * FROM SubjectEntity")
    suspend fun getSubjects(): List<SubjectEntity>

    @Query("SELECT * FROM SubjectEntity WHERE SubjectEntity.isPrimary = 1")
    suspend fun getPrimarySubjects(): List<SubjectEntity>

    @Query("SELECT * FROM SubjectEntity WHERE SubjectEntity.isPrimary = 0")
    suspend fun getNonPrimarySubjects(): List<SubjectEntity>

    @Transaction
    @Query("SELECT * FROM SubjectEntity WHERE SubjectEntity.name = :name")
    suspend fun getSubjectBooksStream(name: String): List<SubjectWithBooks>

    @Query("SELECT COUNT(*) FROM SubjectEntity WHERE SubjectEntity.isPrimary = 1")
    suspend fun getPrimarySubjectsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: SubjectEntity)

}