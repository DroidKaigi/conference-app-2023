package io.github.droidkaigi.confsched2023.data.di

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import io.github.droidkaigi.confsched2023.data.NetworkService
import io.github.droidkaigi.confsched2023.data.auth.AndroidAuthenticator
import io.github.droidkaigi.confsched2023.data.auth.AuthApi
import io.github.droidkaigi.confsched2023.data.auth.Authenticator
import io.github.droidkaigi.confsched2023.data.auth.DefaultAuthApi
import io.github.droidkaigi.confsched2023.data.core.defaultJson
import io.github.droidkaigi.confsched2023.data.core.defaultKtorConfig
import io.github.droidkaigi.confsched2023.data.di.ServerEnvironmentModule.ServerEnvironment
import io.github.droidkaigi.confsched2023.data.remoteconfig.DefaultRemoteConfigApi
import io.github.droidkaigi.confsched2023.data.remoteconfig.RemoteConfigApi
import io.github.droidkaigi.confsched2023.data.user.UserDataStore
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
public class ApiModule {
    @Provides
    @Singleton
    public fun provideNetworkService(
        httpClient: HttpClient,
        authApi: AuthApi,
    ): NetworkService {
        return NetworkService(httpClient, authApi)
    }

    @Provides
    @Singleton
    public fun provideHttpClient(
        okHttpClient: OkHttpClient,
        settingsDatastore: UserDataStore,
        ktorJsonSettings: Json,
    ): HttpClient {
        val httpClient = HttpClient(OkHttp) {
            engine {
                config {
                    preconfigured = okHttpClient
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            // TODO use BuildConfig.DEBUG
                            level = if (true) {
                                HttpLoggingInterceptor.Level.BODY
                            } else {
                                HttpLoggingInterceptor.Level.NONE
                            }
                        },
                    )
                }
            }
            defaultKtorConfig(settingsDatastore, ktorJsonSettings)
        }
        return httpClient
    }

    @Provides
    @Singleton
    fun provideKtorJsonSettings(): Json {
        return defaultJson()
    }

    @Provides
    @Singleton
    public fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        // TODO use BuildConfig.DEBUG
        if (true) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HEADERS
            builder.addNetworkInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }
}

@InstallIn(SingletonComponent::class)
@Module
public class KtorfitModule {
    @Provides
    @Singleton
    public fun provideKtorfit(
        httpClient: HttpClient,
        serverEnvironment: ServerEnvironment,
    ): Ktorfit {
        return Ktorfit
            .Builder()
            .httpClient(httpClient)
            .baseUrl(serverEnvironment.baseUrl)
            .build()
    }
}

@InstallIn(SingletonComponent::class)
@Module
class AuthApiModule {
    @Provides
    @Singleton
    fun provideAuthApi(
        httpClient: HttpClient,
        userDataStore: UserDataStore,
        authenticator: Authenticator,
    ): AuthApi {
        return DefaultAuthApi(httpClient, userDataStore, authenticator)
    }
}

@InstallIn(SingletonComponent::class)
@Module
class AuthenticatorModule {
    @Provides
    @Singleton
    fun provideAuthenticator(): Authenticator {
        return AndroidAuthenticator()
    }
}

@InstallIn(SingletonComponent::class)
@Module
class RemoteConfigModule {

    @Provides
    @Singleton
    fun provideRemoteConfigApi(): RemoteConfigApi {
        return DefaultRemoteConfigApi(ProcessLifecycleOwner.get().lifecycle)
    }
}

@InstallIn(SingletonComponent::class)
@Module
class ServerEnvironmentModule {
    class ServerEnvironment(
        val baseUrl: String,
    )

    interface HasServerEnvironment {
        val serverEnvironment: ServerEnvironment
    }

    @Provides
    @Singleton
    fun provideServerEnvironment(application: Application): ServerEnvironment {
        return (application as HasServerEnvironment).serverEnvironment
    }
}
