package io.github.droidkaigi.confsched2023.data.sessions

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import io.github.droidkaigi.confsched2023.data.NetworkService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SessionsApiModule {
    @Provides
    @Singleton
    fun provideSessionsApi(
        networkService: NetworkService,
        ktorfit: Ktorfit,
    ): SessionsApiClient {
        return DefaultSessionsApiClient(
            networkService = networkService,
            ktorfit = ktorfit,
        )
    }
}
