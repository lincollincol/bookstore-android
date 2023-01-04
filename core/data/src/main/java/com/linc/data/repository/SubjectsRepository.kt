package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.model.asExternalModel
import com.linc.database.dao.SubjectDao
import com.linc.database.entity.subject.SubjectEntity
import com.linc.database.entity.subject.UpdateSubjectPrimary
import com.linc.model.Subject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SubjectsRepository @Inject constructor(
    private val subjectDao: SubjectDao,
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    fun getSubjectStream(id: String): Flow<Subject> {
        return subjectDao.getSubjectStream(id).map(SubjectEntity::asExternalModel)
    }

    suspend fun getSubjects(): List<Subject> = withContext(dispatcher) {
        return@withContext subjectDao.getSubjects().map(SubjectEntity::asExternalModel)
    }

    suspend fun getPrimarySubjects(): List<Subject> = withContext(dispatcher) {
        return@withContext subjectDao.getPrimarySubjects().map(SubjectEntity::asExternalModel)
    }

    suspend fun getNonPrimarySubjects(): List<Subject> = withContext(dispatcher) {
        return@withContext subjectDao.getNonPrimarySubjects().map(SubjectEntity::asExternalModel)
    }

    fun getPrimarySubjectsStream(): Flow<List<Subject>> = subjectDao.getPrimarySubjectsStream()
        .map { it.map(SubjectEntity::asExternalModel) }
        .flowOn(dispatcher)

    fun getNonPrimarySubjectsStream(): Flow<List<Subject>> = subjectDao.getNonPrimarySubjectsStream()
        .map { it.map(SubjectEntity::asExternalModel) }
        .flowOn(dispatcher)

    suspend fun updateSubjectPrimary(
        subjectId: String,
        isPrimary: Boolean
    ) = withContext(dispatcher) {
        subjectDao.updateSubjectPrimary(UpdateSubjectPrimary(subjectId, isPrimary))
    }

}