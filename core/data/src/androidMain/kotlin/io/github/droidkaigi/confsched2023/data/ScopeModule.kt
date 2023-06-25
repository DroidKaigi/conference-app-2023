package io.github.droidkaigi.confsched2023.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
internal object ScopeModule {
    @IoCoroutineScope
    @Provides
    fun providesIoScope(@IoDispatcher dispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(dispatcher + SupervisorJob())

    @DefaultCoroutineScope
    @Provides
    fun providesDefaultScope(@DefaultDispatcher dispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(dispatcher + SupervisorJob())
}
