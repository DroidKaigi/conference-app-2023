package io.github.droidkaigi.confsched2023.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.model.SessionsRepository

@Module
@InstallIn(SingletonComponent::class)
class SessionsRepositoryModule {
    @Provides
    fun provideSessionsRepository(): SessionsRepository {
        return DefaultSessionsRepository(
            FakeSessionsApi()
        )
    }
}