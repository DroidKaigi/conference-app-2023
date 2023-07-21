package io.github.droidkaigi.confsched2023.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2023.data.NetworkService
import io.github.droidkaigi.confsched2023.data.auth.AuthApi
import io.github.droidkaigi.confsched2023.data.auth.Authenticator
import io.github.droidkaigi.confsched2023.data.auth.AuthenticatorImpl
import io.github.droidkaigi.confsched2023.data.auth.DefaultAuthApi
import io.github.droidkaigi.confsched2023.data.user.UserDataStore
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
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
            defaultKtorConfig(settingsDatastore)
        }
        return httpClient
    }

    public fun HttpClientConfig<*>.defaultKtorConfig(
        userDataStore: UserDataStore,
    ) {
        install(ContentNegotiation) {
            json(
                Json {
                    encodeDefaults = true
                    isLenient = true
                    allowSpecialFloatingPointValues = true
                    allowStructuredMapKeys = true
                    prettyPrint = false
                    useArrayPolymorphism = false
                    ignoreUnknownKeys = true
                },
            )
        }

        defaultRequest {
            headers {
                userDataStore.idToken.value?.let {
                    set("Authorization", "Bearer $it")
                }
            }
        }
    }

    @Provides
    @Singleton
    public fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        // TODO use BuildConfig.DEBUG
        if (true) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = BASIC
            builder.addNetworkInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
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
        return AuthenticatorImpl()
    }
}
