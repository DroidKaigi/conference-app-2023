package io.github.droidkaigi.confsched2023.data.remoteconfig

import androidx.datastore.core.IOException

public class FakeRemoteConfigApi : RemoteConfigApi {

    public sealed interface Status : RemoteConfigApi {
        public data object Default : Status {
            override suspend fun getBoolean(key: String): Boolean {
                return true
            }

            override suspend fun getString(key: String): String {
                return "default"
            }
        }

        public data object ThrowException : Status {
            override suspend fun getBoolean(key: String): Boolean {
                throw IOException("FakeRemoteConfigApi throws exception")
            }

            override suspend fun getString(key: String): String {
                throw IOException("FakeRemoteConfigApi throws exception")
            }
        }
    }

    private var status: Status = Status.Default

    public fun setUp(status: Status) {
        this.status = status
    }

    override suspend fun getBoolean(key: String): Boolean {
        return status.getBoolean(key)
    }

    override suspend fun getString(key: String): String {
        return status.getString(key)
    }
}
