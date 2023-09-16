package io.github.droidkaigi.confsched2023.data.di

import dagger.BindsOptionalOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.osslicense.OssLicenseDataSource
import io.github.droidkaigi.confsched2023.model.BuildConfigProvider
import io.github.droidkaigi.confsched2023.model.OssLicenseGroup
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import java.util.Optional
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
public annotation class AppAndroidBuildConfig

@Qualifier
annotation class AppAndroidOssLicenseConfig

@Module
@InstallIn(SingletonComponent::class)
public class BuildConfigProviderModule {
    @Provides
    @Singleton
    public fun provideBuildConfigProvider(
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
public abstract class AppAndroidBuildConfigModule {
    @BindsOptionalOf
    @AppAndroidBuildConfig
    public abstract fun bindBuildConfigProvider(): BuildConfigProvider
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
    override val debugBuild: Boolean = false
}

private object EmptyOssLicenseDataSource : OssLicenseDataSource {
    override suspend fun license(): PersistentList<OssLicenseGroup> {
        return persistentListOf()
    }
}
