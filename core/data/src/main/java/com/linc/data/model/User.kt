package com.linc.data.model

import com.linc.database.entity.user.UserEntity
import com.linc.model.User

fun UserEntity.asExternalModel() = User(
    id = userId,
    name = name
)