package com.linc.data.repository

import android.content.Context
import com.linc.datastore.BookstorePreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val preferencesDataSource: BookstorePreferencesDataSource
) {

    fun getLocaleStream(): Flow<Locale?> = preferencesDataSource.localeStream

    suspend fun saveLocale(locale: String) {
        preferencesDataSource.setLocale(locale)
    }

}