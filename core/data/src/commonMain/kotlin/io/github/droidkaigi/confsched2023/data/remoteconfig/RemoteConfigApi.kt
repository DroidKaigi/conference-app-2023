package io.github.droidkaigi.confsched2023.data.remoteconfig

public interface RemoteConfigApi {
    public suspend fun getBoolean(key: String): Boolean
    public suspend fun getString(key: String): String
}
