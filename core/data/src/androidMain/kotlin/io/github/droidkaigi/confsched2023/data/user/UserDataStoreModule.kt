package io.github.droidkaigi.confsched2023.data.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
public class UserDataStoreModule {

    @Provides
    @Singleton
    public fun provideUserDataStore(
        @UserDataStoreQualifier
        dataStore: DataStore<Preferences>,
    ): UserDataStore {
        return UserDataStore(dataStore)
    }
}
