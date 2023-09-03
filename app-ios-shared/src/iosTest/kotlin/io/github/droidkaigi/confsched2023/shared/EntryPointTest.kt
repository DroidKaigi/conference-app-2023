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
        val kmpEntryPoint = KmpEntryPoint()
        kmpEntryPoint.init(
            remoteConfigApi = object : RemoteConfigApi {
                override suspend fun getBoolean(key: String): Boolean {
                    return true
                }

                override suspend fun getString(key: String): String {
                    return "default"
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
        assertNotNull(kmpEntryPoint.get<UserDataStore>())
        assertNotNull(kmpEntryPoint.get<HttpClient>())
        assertNotNull(kmpEntryPoint.get<Authenticator>())
        assertNotNull(kmpEntryPoint.get<AuthApi>())
        assertNotNull(kmpEntryPoint.get<SessionsApiClient>())
        assertNotNull(kmpEntryPoint.get<SessionCacheDataStore>())

        assertNotNull(kmpEntryPoint.get<SessionsRepository>())
        assertNotNull(kmpEntryPoint.get<StampRepository>())
        assertNotNull(kmpEntryPoint.get<ContributorsRepository>())
        assertNotNull(kmpEntryPoint.get<SponsorsRepository>())
        assertNotNull(kmpEntryPoint.get<StaffRepository>())
    }
}
