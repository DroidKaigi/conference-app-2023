package io.github.droidkaigi.confsched2023.data.navigation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.model.NavigationRequester
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NavigationModule {
    @Provides
    @Singleton
    fun provideNavigationRequester(): NavigationRequester = DefaultNavigationRequester()
}

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
