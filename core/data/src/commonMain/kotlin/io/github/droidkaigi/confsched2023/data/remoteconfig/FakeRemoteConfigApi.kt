package io.github.droidkaigi.confsched2023.data.remoteconfig

import androidx.datastore.core.IOException

class FakeRemoteConfigApi : RemoteConfigApi {

    sealed interface Behavior : RemoteConfigApi {
        object Default : Behavior {
            override suspend fun getBoolean(key: String): Boolean {
                return true
            }
        }

        object ThrowException : Behavior {
            override suspend fun getBoolean(key: String): Boolean {
                throw IOException("FakeRemoteConfigApi throws exception")
            }
        }
    }

    private var behavior: Behavior = Behavior.Default

    fun setUp(behavior: Behavior) {
        this.behavior = behavior
    }

    override suspend fun getBoolean(key: String): Boolean {
        return behavior.getBoolean("key")
    }
}
