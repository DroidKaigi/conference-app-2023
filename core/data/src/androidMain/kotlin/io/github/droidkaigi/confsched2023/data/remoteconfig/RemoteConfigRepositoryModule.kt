package io.github.droidkaigi.confsched2023.data.remoteconfig

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.achievements.AchievementsDataStore
import io.github.droidkaigi.confsched2023.data.achievements.DefaultAchievementRepository
import io.github.droidkaigi.confsched2023.data.contributors.AchievementRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteConfigRepositoryModule {
    @Provides
    @Singleton
    fun provideRemoteConfigRepository(
        remoteConfigApi: RemoteConfigApi,
        achievementsDataStore: AchievementsDataStore,
    ): AchievementRepository {
        return DefaultAchievementRepository(
            remoteConfigApi = remoteConfigApi,
            achievementsDataStore = achievementsDataStore,
        )
    }
}
