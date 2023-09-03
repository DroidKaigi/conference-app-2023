package io.github.droidkaigi.confsched2023

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OssLicenseRepositoryModule {
    @Provides
    @Singleton
    fun provideSponsorsRepository(): OssLicenseRepository {
        return OssLicenseRepositoryImpl()
    }
}
