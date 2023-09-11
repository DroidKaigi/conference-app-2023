package io.github.droidkaigi.confsched2023.data

import de.jensklingenberg.ktorfit.Ktorfit
import io.github.droidkaigi.confsched2023.data.achievements.AchievementsDataStore
import io.github.droidkaigi.confsched2023.data.achievements.DefaultAchievementRepository
import io.github.droidkaigi.confsched2023.data.auth.AuthApi
import io.github.droidkaigi.confsched2023.data.auth.DefaultAuthApi
import io.github.droidkaigi.confsched2023.data.contributors.AchievementRepository
import io.github.droidkaigi.confsched2023.data.contributors.ContributorsApiClient
import io.github.droidkaigi.confsched2023.data.contributors.DefaultContributorsApiClient
import io.github.droidkaigi.confsched2023.data.contributors.DefaultContributorsRepository
import io.github.droidkaigi.confsched2023.data.core.defaultJson
import io.github.droidkaigi.confsched2023.data.core.defaultKtorConfig
import io.github.droidkaigi.confsched2023.data.sessions.DefaultSessionsApiClient
import io.github.droidkaigi.confsched2023.data.sessions.DefaultSessionsRepository
import io.github.droidkaigi.confsched2023.data.sessions.SessionCacheDataStore
import io.github.droidkaigi.confsched2023.data.sessions.SessionsApiClient
import io.github.droidkaigi.confsched2023.data.sponsors.DefaultSponsorsApiClient
import io.github.droidkaigi.confsched2023.data.sponsors.DefaultSponsorsRepository
import io.github.droidkaigi.confsched2023.data.sponsors.SponsorsApiClient
import io.github.droidkaigi.confsched2023.data.staff.DefaultStaffApiClient
import io.github.droidkaigi.confsched2023.data.staff.DefaultStaffRepository
import io.github.droidkaigi.confsched2023.data.staff.StaffApiClient
import io.github.droidkaigi.confsched2023.data.user.UserDataStore
import io.github.droidkaigi.confsched2023.model.ContributorsRepository
import io.github.droidkaigi.confsched2023.model.SessionsRepository
import io.github.droidkaigi.confsched2023.model.SponsorsRepository
import io.github.droidkaigi.confsched2023.model.StaffRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

class BaseUrl(val baseUrl: String)

@OptIn(ExperimentalForeignApi::class)
public val dataModule: Module = module {
    single {
        HttpClient(Darwin) {
            engine {}
            defaultKtorConfig(get(), get())
        }
    }
    single {
        defaultJson()
    }
    single {
        Ktorfit
            .Builder()
            .httpClient(get<HttpClient>())
            .baseUrl(get<BaseUrl>().baseUrl)
            .build()
    }
    single {
        val dataStore = createDataStore(
            coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
            producePath = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/confsched2023.preferences_pb"
            },
        )
        UserDataStore(dataStore)
    }
    single {
        val dataStore = createDataStore(
            coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
            producePath = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/confsched2023.cache.preferences_pb"
            },
        )
        SessionCacheDataStore(dataStore, get())
    }
    single {
        val dataStore = createDataStore(
            coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
            producePath = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/confsched2023.achievements.preferences_pb"
            },
        )
        AchievementsDataStore(dataStore)
    }

    singleOf(::DefaultAuthApi) bind AuthApi::class
    singleOf(::DefaultSessionsApiClient) bind SessionsApiClient::class
    singleOf(::DefaultContributorsApiClient) bind ContributorsApiClient::class
    singleOf(::DefaultSponsorsApiClient) bind SponsorsApiClient::class
    singleOf(::DefaultStaffApiClient) bind StaffApiClient::class

    singleOf(::NetworkService)
    singleOf(::DefaultAchievementRepository) bind AchievementRepository::class
    singleOf(::DefaultSessionsRepository) bind SessionsRepository::class
    singleOf(::DefaultContributorsRepository) bind ContributorsRepository::class
    singleOf(::DefaultStaffRepository) bind StaffRepository::class
    singleOf(::DefaultSponsorsRepository) bind SponsorsRepository::class
}
