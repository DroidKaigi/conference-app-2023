package io.github.droidkaigi.confsched2023.data.remoteconfig

interface RemoteConfigApi {
    suspend fun getBoolean(key: String): Boolean
    suspend fun getString(key: String): String
}
