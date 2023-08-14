package io.github.droidkaigi.confsched2023.data.sessions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.droidkaigi.confsched2023.data.sessions.response.SessionsAllResponse
import io.github.droidkaigi.confsched2023.model.Timetable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SessionCacheDataStore(
    private val dataStore: DataStore<Preferences>,
    private val json: Json,
) {
    internal suspend fun save(sessionsAllResponse: SessionsAllResponse) {
        dataStore.edit { preferences ->
            preferences[DATA_STORE_TIMETABLE_KEY] = json.encodeToString(sessionsAllResponse)
        }
    }

    fun getTimetableStream(): Flow<Timetable> {
        return dataStore.data.map { preferences ->
            val cache = preferences[DATA_STORE_TIMETABLE_KEY] ?: return@map SessionsAllResponse()
            json.decodeFromString(SessionsAllResponse.serializer(), cache)
        }.map {
            it.toTimetable()
        }
    }

    companion object {
        private val DATA_STORE_TIMETABLE_KEY =
            stringPreferencesKey("DATA_STORE_TIMETABLE_KEY")
    }
}
