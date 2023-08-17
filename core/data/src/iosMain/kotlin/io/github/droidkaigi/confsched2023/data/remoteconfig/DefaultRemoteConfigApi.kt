package io.github.droidkaigi.confsched2023.data.remoteconfig

import io.github.droidkaigi.confsched2023.data.remoteconfig.FakeRemoteConfigApi.Status.Default

actual class DefaultRemoteConfigApi : RemoteConfigApi {

    actual override suspend fun getBoolean(key: String): Boolean {
        return FakeRemoteConfigApi().apply {
            setUp(Default)
        }.getBoolean(key)
    }
}
