package io.github.droidkaigi.confsched2023.testing.data.sponsors

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.github.droidkaigi.confsched2023.data.sponsors.FakeSponsorsApiClient
import io.github.droidkaigi.confsched2023.data.sponsors.SponsorsApiClient
import io.github.droidkaigi.confsched2023.data.sponsors.SponsorsApiModule
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SponsorsApiModule::class],
)
class FakeSponsorsApiModule {
    @Provides
    @Singleton
    fun provideSponsorsApi(): SponsorsApiClient {
        return FakeSponsorsApiClient()
    }
}
