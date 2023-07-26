package io.github.droidkaigi.confsched2023.testing.data.session

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.github.droidkaigi.confsched2023.data.session.FakeSessionsApi
import io.github.droidkaigi.confsched2023.data.session.SessionsApiClient
import io.github.droidkaigi.confsched2023.data.session.SessionsApiModule

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SessionsApiModule::class],
)
class FakeSessionsApiModule {
    @Provides
    fun provideSessionsApi(): SessionsApiClient {
        return FakeSessionsApi()
    }
}
