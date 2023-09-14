package io.github.droidkaigi.confsched2023.data.di

import dagger.BindsOptionalOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.model.BuildConfigProvider
import io.github.droidkaigi.confsched2023.model.License
import io.github.droidkaigi.confsched2023.model.OssLicenseRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
        @AppAndroidOssLicenseConfig ossLicenseRepository: Optional<OssLicenseRepository>,
    ): OssLicenseRepository = if (ossLicenseRepository.isPresent) {
        ossLicenseRepository.get()
    } else {
        EmptyOssLicenseRepository
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
    abstract fun bindOssLicenseProvider(): OssLicenseRepository
}

private object EmptyBuildConfigProvider : BuildConfigProvider {
    override val versionName: String = ""
}

private object EmptyOssLicenseRepository : OssLicenseRepository {
    override fun licenseMetaData(): Flow<PersistentList<License>> = flowOf(persistentListOf())
    override fun licenseDetailData(): Flow<List<String>> = flowOf(emptyList())
}
