package io.github.droidkaigi.confsched2023.data.sponsors

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.model.SponsorsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SponsorsRepositoryModule {
    @Provides
    @Singleton
    fun provideSponsorsRepository(
        sponsorsApi: SponsorsApiClient,
    ): SponsorsRepository {
        return DefaultSponsorsRepository(
            sponsorsApi = sponsorsApi,
        )
    }
}
