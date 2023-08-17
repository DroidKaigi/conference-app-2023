package io.github.droidkaigi.confsched2023.data.remoteconfig

import androidx.datastore.core.IOException

class FakeRemoteConfigApi : RemoteConfigApi {

    sealed interface Status : RemoteConfigApi {
        object Default : Status {
            override suspend fun getBoolean(key: String): Boolean {
                return true
            }
        }

        object ThrowException : Status {
            override suspend fun getBoolean(key: String): Boolean {
                throw IOException("FakeRemoteConfigApi throws exception")
            }
        }
    }

    private var status: Status = Status.Default

    fun setUp(status: Status) {
        this.status = status
    }

    override suspend fun getBoolean(key: String): Boolean {
        return status.getBoolean(key)
    }
}
