package io.github.droidkaigi.confsched2023.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.github.droidkaigi.confsched2023.data.FakeSessionsApi
import io.github.droidkaigi.confsched2023.data.SessionsApi
import io.github.droidkaigi.confsched2023.data.SessionsApiModule

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [SessionsApiModule::class])
class FakeSessionsApiModule {
    @Provides
    fun provideSessionsApi(): SessionsApi {
        return FakeSessionsApi()
    }
}