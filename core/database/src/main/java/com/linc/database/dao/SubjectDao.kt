package com.linc.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.linc.database.entity.subject.SubjectWithBooks

@Dao
interface SubjectDao {

    @Transaction
    @Query("SELECT * FROM SubjectEntity WHERE SubjectEntity.name = :name")
    fun getSubjectBooksStream(name: String): List<SubjectWithBooks>

}