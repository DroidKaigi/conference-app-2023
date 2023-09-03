package io.github.droidkaigi.confsched2023.data.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.createDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class UserDataStoreQualifier

@Qualifier
annotation class SessionCacheDataStoreQualifier

@Qualifier
annotation class AchievementsDataStoreQualifier

@InstallIn(SingletonComponent::class)
@Module
class DataStoreModule {

    @UserDataStoreQualifier
    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = createDataStore(
        coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        producePath = { context.filesDir.resolve(DATA_STORE_PREFERENCE_FILE_NAME).path },
    )

    @SessionCacheDataStoreQualifier
    @Provides
    @Singleton
    fun provideSessionCacheDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = createDataStore(
        coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        producePath = { context.cacheDir.resolve(DATA_STORE_CACHE_PREFERENCE_FILE_NAME).path },
    )

    @AchievementsDataStoreQualifier
    @Provides
    @Singleton
    fun provideAchievementsDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = createDataStore(
        coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        producePath = { context.filesDir.resolve(DATA_STORE_ACHIEVEMENTS_FILE_NAME).path },
    )

    companion object {
        private const val DATA_STORE_PREFERENCE_FILE_NAME = "confsched2023.preferences_pb"
        private const val DATA_STORE_CACHE_PREFERENCE_FILE_NAME =
            "confsched2023.cache.preferences_pb"
        private const val DATA_STORE_ACHIEVEMENTS_FILE_NAME =
            "confsched2023.achievements.preferences_pb"
    }
}
