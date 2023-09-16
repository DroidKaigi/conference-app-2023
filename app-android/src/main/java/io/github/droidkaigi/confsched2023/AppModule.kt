package io.github.droidkaigi.confsched2023

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.di.AppAndroidBuildConfig
import io.github.droidkaigi.confsched2023.data.di.AppAndroidOssLicenseConfig
import io.github.droidkaigi.confsched2023.data.osslicense.OssLicenseDataSource
import io.github.droidkaigi.confsched2023.license.DefaultOssLicenseDataSource
import io.github.droidkaigi.confsched2023.model.BuildConfigProvider
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
    @AppAndroidOssLicenseConfig
    fun provideOssLicenseDataSourceProvider(
        @ApplicationContext context: Context,
    ): OssLicenseDataSource = DefaultOssLicenseDataSource(context)
}

class AppBuildConfigProvider(
    override val versionName: String = BuildConfig.VERSION_NAME,
) : BuildConfigProvider
