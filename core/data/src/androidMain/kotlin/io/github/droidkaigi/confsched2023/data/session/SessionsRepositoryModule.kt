package io.github.droidkaigi.confsched2023.data.session

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.user.UserDataStore
import io.github.droidkaigi.confsched2023.model.SessionsRepository

@Module
@InstallIn(SingletonComponent::class)
class SessionsRepositoryModule {
    @Provides
    fun provideSessionsRepository(
        sessionsApi: SessionsApi,
        userDataStore: UserDataStore,
    ): SessionsRepository {
        return DefaultSessionsRepository(
            sessionsApi = sessionsApi,
            userDataStore = userDataStore,
        )
    }
}
