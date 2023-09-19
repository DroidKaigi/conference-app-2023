package io.github.droidkaigi.confsched2023

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.di.AppAndroidBuildConfig
import io.github.droidkaigi.confsched2023.model.BuildConfigProvider
import kotlinx.datetime.Clock
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    @AppAndroidBuildConfig
    fun provideBuildConfigProvider(): BuildConfigProvider = AppBuildConfigProvider()

    @Provides
    @Singleton
    fun provideClockProvider(): ClockProvider = object : ClockProvider {
        override fun clock(): Clock = Clock.System
    }
}

class AppBuildConfigProvider(
    override val versionName: String = BuildConfig.VERSION_NAME,
    override val debugBuild: Boolean = BuildConfig.DEBUG,
) : BuildConfigProvider

interface ClockProvider {
    fun clock(): Clock
}
