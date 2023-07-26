package io.github.droidkaigi.confsched2023.data.session

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit

@Module
@InstallIn(SingletonComponent::class)
class SessionsApiModule {
    @Provides
    fun provideSessionsApi(ktorfit: Ktorfit): SessionsApiClient {
        return DefaultSessionsApiClient(ktorfit)
    }
}
