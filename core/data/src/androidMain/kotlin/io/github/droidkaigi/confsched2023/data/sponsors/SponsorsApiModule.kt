package io.github.droidkaigi.confsched2023.data.sponsors

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import io.github.droidkaigi.confsched2023.data.NetworkService

@Module
@InstallIn(SingletonComponent::class)
class SponsorsApiModule {
    @Provides
    fun provideSponsorsApi(
        networkService: NetworkService,
        ktorfit: Ktorfit,
    ): SponsorsApiClient {
        return DefaultSponsorsApiClient(
            networkService = networkService,
            ktorfit = ktorfit,
        )
    }
}
