package io.github.droidkaigi.confsched2023.data.stamps

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class StampDataStore(
    private val dataStore: DataStore<Preferences>,
) {
    internal suspend fun save(
        isDisplayed: Boolean,
    ) {
        dataStore.edit { preferences ->
            preferences[DATA_STORE_STAMPS_KEY] = isDisplayed.toString()
        }
    }

    public fun isDisplayedStream(): Flow<Boolean?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences: Preferences ->
                preferences[DATA_STORE_STAMPS_KEY]?.toBoolean()
            }
    }

    companion object {
        private val DATA_STORE_STAMPS_KEY =
            stringPreferencesKey("DATA_STORE_STAMPS_KEY")
    }
}
