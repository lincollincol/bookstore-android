package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.model.asExternalModel
import com.linc.database.dao.SubjectDao
import com.linc.database.entity.subject.SubjectEntity
import com.linc.model.Subject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SubjectsRepository @Inject constructor(
    private val subjectDao: SubjectDao,
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    suspend fun getSubjects(): List<Subject> = withContext(dispatcher) {
        return@withContext subjectDao.getSubjects().map(SubjectEntity::asExternalModel)
    }

    suspend fun getPrimarySubjects(): List<Subject> = withContext(dispatcher) {
        return@withContext subjectDao.getPrimarySubjects().map(SubjectEntity::asExternalModel)
    }

    suspend fun getNonPrimarySubjects(): List<Subject> = withContext(dispatcher) {
        return@withContext subjectDao.getNonPrimarySubjects().map(SubjectEntity::asExternalModel)
    }

    fun saveSubject() {

    }

}