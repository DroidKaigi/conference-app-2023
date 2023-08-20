package io.github.droidkaigi.confsched2023.shared

import io.github.droidkaigi.confsched2023.data.auth.AuthApi
import io.github.droidkaigi.confsched2023.data.auth.Authenticator
import io.github.droidkaigi.confsched2023.data.auth.User
import io.github.droidkaigi.confsched2023.data.contributors.StampRepository
import io.github.droidkaigi.confsched2023.data.remoteconfig.RemoteConfigApi
import io.github.droidkaigi.confsched2023.data.sessions.SessionCacheDataStore
import io.github.droidkaigi.confsched2023.data.sessions.SessionsApiClient
import io.github.droidkaigi.confsched2023.data.user.UserDataStore
import io.github.droidkaigi.confsched2023.model.ContributorsRepository
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.SponsorsRepository
import io.github.droidkaigi.confsched2023.model.StaffRepository
import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.test.Test
import kotlin.test.assertNotNull

class EntryPointTest {
    @Test
    fun get() {
        val koinApplication = EntryPoint.koinApplication(
            baseUrl = "https://ssot-api-staging.an.r.appspot.com/",
            remoteConfigApi = object : RemoteConfigApi {
                override suspend fun getBoolean(key: String): Boolean {
                    return true
                }
            },
            authenticator = object : Authenticator {
                override suspend fun currentUser(): User? {
                    return null
                }

                override suspend fun signInAnonymously(): User? {
                    return null
                }
            }
        )
        // Check finer dependencies first to debug easily
        assertNotNull(koinApplication.koin.get<UserDataStore>())
        assertNotNull(koinApplication.koin.get<HttpClient>())
        assertNotNull(koinApplication.koin.get<Authenticator>())
        assertNotNull(koinApplication.koin.get<AuthApi>())
        assertNotNull(koinApplication.koin.get<SessionsApiClient>())
        assertNotNull(koinApplication.koin.get<SessionCacheDataStore>())

        assertNotNull(koinApplication.koin.get<SessionsRepository>())
        assertNotNull(koinApplication.koin.get<StampRepository>())
        assertNotNull(koinApplication.koin.get<ContributorsRepository>())
        assertNotNull(koinApplication.koin.get<SponsorsRepository>())
        assertNotNull(koinApplication.koin.get<StaffRepository>())
    }
}