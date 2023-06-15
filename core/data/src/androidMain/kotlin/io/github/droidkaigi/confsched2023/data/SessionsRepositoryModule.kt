package io.github.droidkaigi.confsched2023.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SessionsRepositoryModule {
    @Provides
    fun provideSessionsRepository(
        sessionsApi: SessionsApi,
        dataStore: DataStore<Preferences>,
        @DefaultCoroutineScope defaultCoroutineScope: CoroutineScope,
    ): SessionsRepository {
        return DefaultSessionsRepository(
            sessionsApi = sessionsApi,
            dataStore = dataStore,
            coroutineScope = defaultCoroutineScope
        )
    }

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context,
        @DefaultCoroutineScope defaultCoroutineScope: CoroutineScope
    ): DataStore<Preferences> = createDataStore(
        coroutineScope = defaultCoroutineScope,
        producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
    )
}
