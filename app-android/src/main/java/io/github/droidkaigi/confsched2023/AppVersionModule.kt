package io.github.droidkaigi.confsched2023

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.model.AppVersion
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppVersionModule {

    @Provides
    @Singleton
    fun provideAppVersion(): AppVersion {
        return AppVersion(
            packageName = BuildConfig.APPLICATION_ID,
            versionName = BuildConfig.VERSION_NAME,
            versionCode = BuildConfig.VERSION_CODE,
        )
    }
}
