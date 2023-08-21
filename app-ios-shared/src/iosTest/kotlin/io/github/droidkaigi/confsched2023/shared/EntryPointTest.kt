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
import kotlin.test.Test
import kotlin.test.assertNotNull

class EntryPointTest {
    @Test
    fun get() {
        val entryPoint = EntryPoint()
        entryPoint.init(
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
        assertNotNull(entryPoint.get<UserDataStore>())
        assertNotNull(entryPoint.get<HttpClient>())
        assertNotNull(entryPoint.get<Authenticator>())
        assertNotNull(entryPoint.get<AuthApi>())
        assertNotNull(entryPoint.get<SessionsApiClient>())
        assertNotNull(entryPoint.get<SessionCacheDataStore>())

        assertNotNull(entryPoint.get<SessionsRepository>())
        assertNotNull(entryPoint.get<StampRepository>())
        assertNotNull(entryPoint.get<ContributorsRepository>())
        assertNotNull(entryPoint.get<SponsorsRepository>())
        assertNotNull(entryPoint.get<StaffRepository>())
    }
}