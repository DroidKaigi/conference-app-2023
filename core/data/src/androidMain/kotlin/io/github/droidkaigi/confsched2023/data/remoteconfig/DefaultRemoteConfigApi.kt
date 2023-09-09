package io.github.droidkaigi.confsched2023.data.remoteconfig

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.get
import dev.gitlive.firebase.remoteconfig.remoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DefaultRemoteConfigApi : RemoteConfigApi {

    private val firebaseRemoteConfig = Firebase.remoteConfig

    init {
        CoroutineScope(Dispatchers.IO).launch {
            firebaseRemoteConfig.reset()
            fetchConfig()
        }
    }

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
            Logger.e(e.message ?: "FirebaseRemoteConfig fetchAndActivate failed")
        }
    }
}
