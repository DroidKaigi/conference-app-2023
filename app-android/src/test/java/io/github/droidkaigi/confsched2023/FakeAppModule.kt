package io.github.droidkaigi.confsched2023

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class],
)
@Module
class FakeAppModule {
    @Provides
    @Singleton
    fun provideClockProvider(): ClockProvider = object : ClockProvider {
        override fun clock(): Clock = object : Clock {
            override fun now() = Instant.parse("2023-09-14T10:00:00.00Z")
        }
    }
}
