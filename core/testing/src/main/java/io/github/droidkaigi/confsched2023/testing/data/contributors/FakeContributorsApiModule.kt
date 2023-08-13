package io.github.droidkaigi.confsched2023.testing.data.contributors

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.github.droidkaigi.confsched2023.data.contributors.ContributorsApiClient
import io.github.droidkaigi.confsched2023.data.contributors.ContributorsApiModule
import io.github.droidkaigi.confsched2023.data.contributors.FakeContributorsApiClient
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ContributorsApiModule::class],
)
class FakeContributorsApiModule {
    @Provides
    @Singleton
    fun provideSessionsApi(): ContributorsApiClient {
        return FakeContributorsApiClient()
    }
}
