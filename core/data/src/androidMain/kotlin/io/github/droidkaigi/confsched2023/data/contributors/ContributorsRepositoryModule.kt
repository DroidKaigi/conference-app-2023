package io.github.droidkaigi.confsched2023.data.contributors

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ContributorsRepositoryModule {
    @Provides
    fun provideContributorsRepository(
        contributorsApi: ContributorsApiClient,
    ): ContributorsRepository {
        return DefaultContributorsRepository(
            contributorsApi = contributorsApi,
        )
    }
}
