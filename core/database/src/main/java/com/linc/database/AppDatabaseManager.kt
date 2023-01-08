package com.linc.database

import javax.inject.Inject

class AppDatabaseManager @Inject constructor(
    private val database: BookstoreDatabase
) {
    suspend fun clearDatabase() {
        database.clearUserDataTables()
    }
}