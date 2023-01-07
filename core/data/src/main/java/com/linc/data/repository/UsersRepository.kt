package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.model.asExternalModel
import com.linc.database.dao.UsersDao
import com.linc.database.entity.user.UserEntity
import com.linc.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val usersDao: UsersDao,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun createUser(name: String) = withContext(ioDispatcher) {
        usersDao.insertUser(UserEntity(UUID.randomUUID().toString(), name))
    }

    suspend fun getCurrentUser(): User? = withContext(ioDispatcher) {
        return@withContext usersDao.getUser()?.asExternalModel()
    }
}