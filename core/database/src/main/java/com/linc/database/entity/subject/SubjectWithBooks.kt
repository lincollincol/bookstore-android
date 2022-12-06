package com.linc.database.entity.subject

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.linc.database.entity.book.BookEntity

data class SubjectWithBooks(
    @Embedded val subject: SubjectEntity,
    @Relation(
        parentColumn = "subjectId",
        entityColumn = "bookId",
        associateBy = Junction(SubjectBookCrossRef::class)
    )
    val books: List<BookEntity>
)