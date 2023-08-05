package io.github.droidkaigi.confsched2023.testing.data.sessions

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.github.droidkaigi.confsched2023.data.sessions.FakeSessionsApiClient
import io.github.droidkaigi.confsched2023.data.sessions.SessionsApiClient
import io.github.droidkaigi.confsched2023.data.sessions.SessionsApiModule

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SessionsApiModule::class],
)
class FakeSessionsApiModule {
    @Provides
    fun provideSessionsApi(): SessionsApiClient {
        return FakeSessionsApiClient()
    }
}
