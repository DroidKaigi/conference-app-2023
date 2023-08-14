package io.github.droidkaigi.confsched2023.data.sessions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.user.SessionCacheDataStoreQualifier
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SessionCacheDatastoreModule {
    @Provides
    @Singleton
    internal fun provideSessionCacheDataStore(
        json: Json,
        @SessionCacheDataStoreQualifier
        cacheDataStore: DataStore<Preferences>,
    ): SessionCacheDataStore {
        return SessionCacheDataStore(
            dataStore = cacheDataStore,
            json = json,
        )
    }
}
