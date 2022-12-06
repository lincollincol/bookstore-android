package com.linc.database.entity.subject

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name"], unique = true)])
data class SubjectEntity(
    @PrimaryKey
    val subjectId: String,
    val name: String,
)