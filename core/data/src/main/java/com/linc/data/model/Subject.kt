package com.linc.data.model

import com.linc.database.entity.subject.SubjectEntity
import com.linc.model.Subject

fun SubjectEntity.asExternalModel() = Subject(
    id = subjectId,
    name = name,
    isPrimary = isPrimary
)