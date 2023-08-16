package io.github.droidkaigi.confsched2023.testing.data.remoteconfig

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.github.droidkaigi.confsched2023.data.di.RemoteConfigModule
import io.github.droidkaigi.confsched2023.data.remoteconfig.FakeRemoteConfigApi
import io.github.droidkaigi.confsched2023.data.remoteconfig.RemoteConfigApi

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RemoteConfigModule::class],
)
class FakeRemoteConfigApiModule {
    @Provides
    fun provideRemoteConfigApi(): RemoteConfigApi {
        return FakeRemoteConfigApi()
    }
}
