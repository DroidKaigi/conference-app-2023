package io.github.droidkaigi.confsched2023.data.contributors

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import io.github.droidkaigi.confsched2023.data.NetworkService

@Module
@InstallIn(SingletonComponent::class)
class ContributorsApiModule {
    @Provides
    fun provideContributorsApi(
        networkService: NetworkService,
        ktorfit: Ktorfit
    ): ContributorsApiClient {
        return DefaultContributorsApiClient(
            networkService = networkService,
            ktorfit = ktorfit
        )
    }
}
