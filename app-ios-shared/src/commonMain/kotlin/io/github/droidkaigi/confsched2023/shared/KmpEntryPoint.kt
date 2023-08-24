package io.github.droidkaigi.confsched2023.shared

import io.github.droidkaigi.confsched2023.data.BaseUrl
import io.github.droidkaigi.confsched2023.data.auth.Authenticator
import io.github.droidkaigi.confsched2023.data.dataModule
import io.github.droidkaigi.confsched2023.data.remoteconfig.RemoteConfigApi
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.reflect.KClass

class KmpEntryPoint {
    private val defaultBaseUrl = "https://ssot-api-staging.an.r.appspot.com/"

    // Please EntryPoint.get() instead of this property
    lateinit var koinApplication: KoinApplication

    fun init(
        remoteConfigApi: RemoteConfigApi,
        authenticator: Authenticator,
    ) {
        koinApplication = startKoin {
            modules(
                dataModule,
                module {
                    single {
                        BaseUrl(defaultBaseUrl)
                    }
                    single {
                        remoteConfigApi
                    }
                    single {
                        authenticator
                    }
                }
            )
        }
    }

    inline fun <reified T : Any> get(): T {
        return koinApplication.koin.get(T::class)
    }

    fun <T : Any> get(clazz: KClass<T>): T {
        return koinApplication.koin.get(clazz)
    }
}
