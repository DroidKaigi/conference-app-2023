package io.github.droidkaigi.confsched2023.data.sessions

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.auth.AuthApi
import io.github.droidkaigi.confsched2023.data.user.UserDataStore
import io.github.droidkaigi.confsched2023.model.SessionsRepository

@Module
@InstallIn(SingletonComponent::class)
class SessionsRepositoryModule {
    @Provides
    fun provideSessionsRepository(
        sessionsApi: SessionsApiClient,
        authApi: AuthApi,
        userDataStore: UserDataStore,
    ): SessionsRepository {
        return DefaultSessionsRepository(
            sessionsApi = sessionsApi,
            authApi = authApi,
            userDataStore = userDataStore,
        )
    }
}
