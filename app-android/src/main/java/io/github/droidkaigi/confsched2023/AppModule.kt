package io.github.droidkaigi.confsched2023

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.di.AppAndroidBuildConfig
import io.github.droidkaigi.confsched2023.model.BuildConfigProvider
import io.github.droidkaigi.confsched2023.model.NavigationRequester
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
    fun provideNavigationRequester(): NavigationRequester = DefaultNavigationRequester()
}

class AppBuildConfigProvider(
    override val versionName: String = BuildConfig.VERSION_NAME,
) : BuildConfigProvider

class DefaultNavigationRequester : NavigationRequester {
    private val _navigateRequestStateFlow = MutableStateFlow("")
    override fun getNavigationRouteFlow(): Flow<String> = _navigateRequestStateFlow

    override fun navigateTo(route: String) {
        _navigateRequestStateFlow.value = route
    }

    override fun navigated() {
        _navigateRequestStateFlow.value = ""
    }
}
