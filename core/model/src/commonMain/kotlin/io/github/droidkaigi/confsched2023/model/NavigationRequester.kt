package io.github.droidkaigi.confsched2023.model

import kotlinx.coroutines.flow.Flow

interface NavigationRequester {
    fun getNavigationRouteFlow(): Flow<String>
    fun navigateTo(route: String)
    fun navigated()
}
