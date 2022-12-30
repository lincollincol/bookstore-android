package com.linc.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class BookstorePreferencesDataSource @Inject constructor(
    private val preferencesDataStore: DataStore<Preferences>
) {

    private val LOCALE = stringPreferencesKey("locale")

    val localeStream: Flow<Locale?> get() =
        preferencesDataStore.data.map { it[LOCALE]?.let(::Locale) }

    suspend fun setLocale(locale: String) {
        preferencesDataStore.edit { it[LOCALE] = locale }
    }

}

