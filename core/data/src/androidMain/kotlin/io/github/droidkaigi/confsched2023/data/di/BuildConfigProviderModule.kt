package io.github.droidkaigi.confsched2023.data.di

import dagger.BindsOptionalOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.osslicense.OssLicenseDataSource
import io.github.droidkaigi.confsched2023.model.BuildConfigProvider
import io.github.droidkaigi.confsched2023.model.OssLicense
import java.util.Optional
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class AppAndroidBuildConfig

@Qualifier
annotation class AppAndroidOssLicenseConfig

@Module
@InstallIn(SingletonComponent::class)
class BuildConfigProviderModule {
    @Provides
    @Singleton
    fun provideBuildConfigProvider(
        @AppAndroidBuildConfig buildConfigOverride: Optional<BuildConfigProvider>,
    ): BuildConfigProvider = if (buildConfigOverride.isPresent) {
        buildConfigOverride.get()
    } else {
        EmptyBuildConfigProvider
    }

    @Provides
    @Singleton
    fun provideOssLicenseRepositoryProvider(
        @AppAndroidOssLicenseConfig ossLicenseDataSourceOptional: Optional<OssLicenseDataSource>,
    ): OssLicenseDataSource = if (ossLicenseDataSourceOptional.isPresent) {
        ossLicenseDataSourceOptional.get()
    } else {
        EmptyOssLicenseDataSource
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class AppAndroidBuildConfigModule {
    @BindsOptionalOf
    @AppAndroidBuildConfig
    abstract fun bindBuildConfigProvider(): BuildConfigProvider
}

@InstallIn(SingletonComponent::class)
@Module
abstract class AppAndroidOssLicenseModule {
    @BindsOptionalOf
    @AppAndroidOssLicenseConfig
    abstract fun bindOssLicenseDataStoreProvider(): OssLicenseDataSource
}

private object EmptyBuildConfigProvider : BuildConfigProvider {
    override val versionName: String = ""
}

private object EmptyOssLicenseDataSource : OssLicenseDataSource {
    override suspend fun licenseFlow(): OssLicense {
        return OssLicense()
    }
}
