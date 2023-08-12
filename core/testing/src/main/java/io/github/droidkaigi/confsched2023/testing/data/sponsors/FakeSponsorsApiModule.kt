package io.github.droidkaigi.confsched2023.testing.data.sponsors

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.github.droidkaigi.confsched2023.data.sessions.SessionsApiModule
import io.github.droidkaigi.confsched2023.data.sponsors.FakeSponsorsApiClient
import io.github.droidkaigi.confsched2023.data.sponsors.SponsorsApiClient

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SessionsApiModule::class],
)
class FakeSponsorsApiModule {
    @Provides
    fun provideSponsorsApi(): SponsorsApiClient {
        return FakeSponsorsApiClient()
    }
}
