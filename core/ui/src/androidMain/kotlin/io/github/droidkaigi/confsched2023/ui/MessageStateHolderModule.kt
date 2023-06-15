package io.github.droidkaigi.confsched2023.ui

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MessageStateHolderModule {
    @Provides
    fun provideMessageStateHolder(): UserMessageStateHolder {
        return UserMessageStateHolderImpl()
    }
}
