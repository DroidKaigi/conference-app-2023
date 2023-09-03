package io.github.droidkaigi.confsched2023.data.stamps

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.user.StampDataStoreQualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StampsDataStoreModule {

    @Provides
    @Singleton
    fun provideStampsDataStore(
        @StampDataStoreQualifier
        dataStore: DataStore<Preferences>,
    ): StampDataStore {
        return StampDataStore(dataStore)
    }
}
