package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.linc.database.entity.subject.SubjectBookCrossRef
import com.linc.database.entity.subject.SubjectEntity
import com.linc.database.entity.subject.SubjectWithBooks
import com.linc.database.entity.subject.UpdateSubjectPrimary
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Query("SELECT * FROM SubjectEntity WHERE SubjectEntity.subjectId = :id")
    suspend fun getSubject(id: String): SubjectEntity?

    @Query("SELECT * FROM SubjectEntity")
    suspend fun getSubjects(): List<SubjectEntity>

    @Query("SELECT * FROM SubjectEntity WHERE SubjectEntity.isPrimary = 1")
    suspend fun getPrimarySubjects(): List<SubjectEntity>

    @Query("SELECT * FROM SubjectEntity WHERE SubjectEntity.isPrimary = 0")
    suspend fun getNonPrimarySubjects(): List<SubjectEntity>

    @Query("SELECT * FROM SubjectEntity WHERE SubjectEntity.isPrimary = 1")
    fun getPrimarySubjectsStream(): Flow<List<SubjectEntity>>

    @Query("SELECT * FROM SubjectEntity WHERE SubjectEntity.isPrimary = 0")
    fun getNonPrimarySubjectsStream(): Flow<List<SubjectEntity>>

    @Transaction
    @Query("SELECT * FROM SubjectEntity WHERE SubjectEntity.name = :name")
    suspend fun getSubjectBooks(name: String): List<SubjectWithBooks>

    @Transaction
    @Query("SELECT * FROM SubjectEntity WHERE SubjectEntity.name IN (:subjects)")
    fun getSubjectBooksStream(subjects: List<String>): Flow<List<SubjectWithBooks>>

    @Query("SELECT COUNT(*) FROM SubjectEntity WHERE SubjectEntity.isPrimary = 1")
    suspend fun getPrimarySubjectsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: SubjectEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjectBookCrossRef(crossRef: SubjectBookCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjectBookCrossRefs(crossRefs: List<SubjectBookCrossRef>)

    @Update(entity = SubjectEntity::class)
    suspend fun updateSubjectPrimary(primaryUpdate: UpdateSubjectPrimary)

}