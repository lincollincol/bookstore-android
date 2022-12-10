package com.linc.data.model

import com.linc.database.entity.book.BookEntity
import com.linc.database.entity.subject.SubjectEntity
import com.linc.database.entity.subject.SubjectWithBooks
import com.linc.model.Subject
import com.linc.model.SubjectBooks

fun SubjectEntity.asExternalModel() = Subject(
    id = subjectId,
    name = name,
    isPrimary = isPrimary
)

fun SubjectWithBooks.asExternalModel() = SubjectBooks(
    subject = subject.asExternalModel(),
    books = books.map(BookEntity::asExternalModel)
)