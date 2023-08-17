package io.github.droidkaigi.confsched2023.data.remoteconfig

expect class DefaultRemoteConfigApi : RemoteConfigApi {
    override suspend fun getBoolean(key: String): Boolean
}
