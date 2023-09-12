package io.github.droidkaigi.confsched2023.license

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.model.OssLicenseRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OssLicenseRepositoryModule {
    @Provides
    @Singleton
    fun provideSponsorsRepository(
        @ApplicationContext context: Context,
    ): OssLicenseRepository {
        return OssLicenseRepositoryImpl(context = context)
    }
}
