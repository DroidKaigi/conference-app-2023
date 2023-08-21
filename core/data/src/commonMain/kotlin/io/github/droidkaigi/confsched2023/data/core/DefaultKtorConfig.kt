package io.github.droidkaigi.confsched2023.data.core

import io.github.droidkaigi.confsched2023.data.user.UserDataStore
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

public fun HttpClientConfig<*>.defaultKtorConfig(
    userDataStore: UserDataStore,
    ktorJsonSettings: Json,
) {
    install(ContentNegotiation) {
        json(
            ktorJsonSettings,
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

public fun defaultJson(): Json {
    return Json {
        encodeDefaults = true
        isLenient = true
        allowSpecialFloatingPointValues = true
        allowStructuredMapKeys = true
        prettyPrint = false
        useArrayPolymorphism = false
        ignoreUnknownKeys = true
    }
}