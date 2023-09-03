package io.github.droidkaigi.confsched2023.data.achievements

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.achivements.AchievementsDataStore
import io.github.droidkaigi.confsched2023.data.user.AchievementsDataStoreQualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AchievementsDataStoreModule {

    @Provides
    @Singleton
    fun provideAchievementsDataStore(
        @AchievementsDataStoreQualifier
        dataStore: DataStore<Preferences>,
    ): AchievementsDataStore {
        return AchievementsDataStore(dataStore)
    }
}
