package com.linc.database.entity.subject

import androidx.room.Entity

@Entity(primaryKeys = ["bookId", "subjectId"])
data class SubjectBookCrossRef(
    val bookId: String,
    val subjectId: String,
)