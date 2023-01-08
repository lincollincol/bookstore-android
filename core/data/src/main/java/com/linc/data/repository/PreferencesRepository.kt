package com.linc.data.repository

import android.util.Log
import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.common.coroutines.extensions.EMPTY
import com.linc.data.model.asEntity
import com.linc.data.model.asExternalModel
import com.linc.database.AppDatabaseManager
import com.linc.database.BookstoreDatabase
import com.linc.database.dao.LocaleDao
import com.linc.database.entity.localization.LocaleEntity
import com.linc.datastore.PreferencesLocalDataSource
import com.linc.filestore.LocalizationLocalDataSource
import com.linc.filestore.model.LocaleStringsModel
import com.linc.model.AppLocale
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val preferencesLocalDataSource: PreferencesLocalDataSource,
    private val localizationLocalDataSource: LocalizationLocalDataSource,
    private val localeDao: LocaleDao,
    private val databaseManager: AppDatabaseManager,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun clearAppData() = withContext(ioDispatcher) {
        databaseManager.clearDatabase()
        preferencesLocalDataSource.clearData()
    }

    fun getAppLocaleStream(): Flow<Locale?> = preferencesLocalDataSource.localeStream

    fun getAppLocaleStringsStream(): Flow<Map<String, String>> = localeDao.getLocaleStringsStream()
        .map { strings -> strings.associate { it.key to it.value } }
        .catch { it.printStackTrace() }
        .flowOn(ioDispatcher)

    suspend fun saveLocale(locale: String) {
        preferencesLocalDataSource.setLocale(locale)
    }

    suspend fun fetchLatestLocale() = withContext(ioDispatcher) {
        val code = preferencesLocalDataSource.localeStream.first().language
        val localLocaleVersion = localeDao.getLocaleByCode(code)
        val localeVersion = localizationLocalDataSource.getLocalesVersion()

        val isLocaleVersionUpdated = localeVersion != localLocaleVersion?.version
        val isLocaleCodeUpdated = code != localLocaleVersion?.code
        val shouldUpdateLocale = isLocaleCodeUpdated || isLocaleVersionUpdated

        if(localLocaleVersion != null && !shouldUpdateLocale ) {
            return@withContext
        }

        with(localeDao) {
            clearTable()
            insertLocale(LocaleEntity(code, localeVersion))
            localizationLocalDataSource.getLocale(code)
                .map { it.asEntity(code) }
                .also { localeDao.insertLocaleStrings(it) }
        }
    }

    fun getAvailableLocalesStream(): Flow<List<Locale>> =
        flowOf(localizationLocalDataSource.getAvailableLocales()).flowOn(ioDispatcher)

}