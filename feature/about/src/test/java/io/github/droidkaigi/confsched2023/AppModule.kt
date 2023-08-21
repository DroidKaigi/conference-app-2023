package io.github.droidkaigi.confsched2023

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.model.AppVersion
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppVersion(): AppVersion {
        return AppVersion(
            packageName = "io.github.droidkaigi.confsched2023",
            versionName = "1.0.0",
            versionCode = 1,
        )
    }
}