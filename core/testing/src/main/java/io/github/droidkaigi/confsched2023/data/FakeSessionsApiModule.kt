package io.github.droidkaigi.confsched2023.data

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [SessionsApiModule::class])
class FakeSessionsApiModule {
    @Provides
    fun provideSessionsApi(): SessionsApi {
        return FakeSessionsApi()
    }
}
