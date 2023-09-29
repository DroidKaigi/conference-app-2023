package io.github.droidkaigi.confsched2023.data.osslicense

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.model.OssLicenseRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class OssLicenseRepositoryModule {
    @Provides
    @Singleton
    public fun provideOssLicenseRepository(
        ossLicenseDataSource: OssLicenseDataSource,
    ): OssLicenseRepository {
        return DefaultOssLicenseRepository(
            ossLicenseDataSource,
        )
    }
}
