package io.github.droidkaigi.confsched2023.data.remoteconfig

import dev.gitlive.firebase.remoteconfig.FirebaseRemoteConfig
import dev.gitlive.firebase.remoteconfig.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultRemoteConfigApi @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
) : RemoteConfigApi {

    override suspend fun getBoolean(key: String): Boolean {
        fetchConfig()
        return firebaseRemoteConfig[key]
    }

    override suspend fun getString(key: String): String {
        fetchConfig()
        return firebaseRemoteConfig[key]
    }

    private suspend fun fetchConfig() {
        try {
            firebaseRemoteConfig.fetchAndActivate()
        } catch (e: Exception) {
            // handle or log exception
        }
    }
}
