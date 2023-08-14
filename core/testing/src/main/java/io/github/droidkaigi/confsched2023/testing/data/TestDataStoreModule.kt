package io.github.droidkaigi.confsched2023.testing.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.github.droidkaigi.confsched2023.data.createDataStore
import io.github.droidkaigi.confsched2023.data.user.DataStoreModule
import io.github.droidkaigi.confsched2023.data.user.SessionCacheDataStoreQualifier
import io.github.droidkaigi.confsched2023.data.user.UserDataStoreQualifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DataStoreModule::class])
class TestDataStoreModule {

    @Provides
    @Singleton
    @UserDataStoreQualifier
    fun provideDataStore(
        @ApplicationContext context: Context,
        testDispatcher: TestDispatcher,
    ): DataStore<Preferences> = createDataStore(
        coroutineScope = CoroutineScope(testDispatcher + Job()),
        producePath = {
            context.filesDir.resolve(TEST_DATASTORE_NAME).path
        },
    )

    @Provides
    @Singleton
    @SessionCacheDataStoreQualifier
    fun provideSessionCacheDataStore(
        @ApplicationContext context: Context,
        testDispatcher: TestDispatcher,
    ): DataStore<Preferences> = createDataStore(
        coroutineScope = CoroutineScope(testDispatcher + Job()),
        producePath = {
            context.cacheDir.resolve(TEST_DATASTORE_CACHE_NAME).path
        },
    )

    companion object {
        private const val TEST_DATASTORE_NAME = "test_datastore.preferences_pb"
        private const val TEST_DATASTORE_CACHE_NAME = "test_datastore_cache.preferences_pb"
    }
}
