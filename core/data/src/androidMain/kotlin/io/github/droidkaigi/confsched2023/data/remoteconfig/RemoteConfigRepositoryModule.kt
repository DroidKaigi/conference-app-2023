package io.github.droidkaigi.confsched2023.data.remoteconfig

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.contributors.StampRepository
import io.github.droidkaigi.confsched2023.data.stamps.StampDataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteConfigRepositoryModule {
    @Provides
    @Singleton
    fun provideRemoteConfigRepository(
        remoteConfigApi: RemoteConfigApi,
        stampDataStore: StampDataStore,
    ): StampRepository {
        return DefaultStampRepository(
            remoteConfigApi = remoteConfigApi,
            stampDataStore = stampDataStore,
        )
    }
}
