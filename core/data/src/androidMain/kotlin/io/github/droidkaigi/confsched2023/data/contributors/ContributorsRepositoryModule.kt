package io.github.droidkaigi.confsched2023.data.contributors

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.model.ContributorsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContributorsRepositoryModule {
    @Provides
    @Singleton
    fun provideContributorsRepository(
        contributorsApi: ContributorsApiClient,
    ): ContributorsRepository {
        return DefaultContributorsRepository(
            contributorsApi = contributorsApi,
        )
    }
}
