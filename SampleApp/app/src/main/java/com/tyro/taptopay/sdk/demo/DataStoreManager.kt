package com.tyro.taptopay.sdk.demo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    companion object {
        private val Context.appConfigurations: DataStore<Preferences> by preferencesDataStore(
            name = "App Configurations",
        )

        object PreferenceKeys {
            val READER_ID_KEY = stringPreferencesKey("reader_id")
        }
    }

    suspend fun storeReaderId(readerId: String) {
        context.appConfigurations.edit { preferences ->
            preferences[PreferenceKeys.READER_ID_KEY] = readerId
        }
    }

    fun getReaderIdFlow(): Flow<String> {
        val readerIdFlow: Flow<String> =
            context.appConfigurations.data
                .map { preferences ->
                    preferences[PreferenceKeys.READER_ID_KEY] ?: ""
                }
        return readerIdFlow
    }
}
