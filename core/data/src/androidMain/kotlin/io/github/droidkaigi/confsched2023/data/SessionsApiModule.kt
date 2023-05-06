package io.github.droidkaigi.confsched2023.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SessionsApiModule {
    @Provides
    fun provideSessionsApi(): SessionsApi {
        return FakeSessionsApi()
    }
}