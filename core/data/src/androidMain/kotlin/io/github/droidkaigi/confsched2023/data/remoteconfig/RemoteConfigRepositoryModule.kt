package io.github.droidkaigi.confsched2023.data.remoteconfig

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
<<<<<<< HEAD
import io.github.droidkaigi.confsched2023.data.achievements.AchievementsDataStore
import io.github.droidkaigi.confsched2023.data.contributors.StampRepository
=======
import io.github.droidkaigi.confsched2023.data.contributors.AchievementRepository
>>>>>>> origin/main
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteConfigRepositoryModule {
    @Provides
    @Singleton
    fun provideRemoteConfigRepository(
        remoteConfigApi: RemoteConfigApi,
<<<<<<< HEAD
        achievementsDataStore: AchievementsDataStore,
    ): StampRepository {
        return DefaultStampRepository(
=======
    ): AchievementRepository {
        return DefaultAchievementRepository(
>>>>>>> origin/main
            remoteConfigApi = remoteConfigApi,
            achievementsDataStore = achievementsDataStore,
        )
    }
}
