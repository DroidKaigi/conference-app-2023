package io.github.droidkaigi.confsched2023.data.remoteconfig

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.get
import dev.gitlive.firebase.remoteconfig.remoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DefaultRemoteConfigApi : RemoteConfigApi {

    private val firebaseRemoteConfig = Firebase.remoteConfig

    /**
     * If you want to change the interval time to fetch, please change it here
     */
    init {
        CoroutineScope(Dispatchers.IO).launch {
            firebaseRemoteConfig.settings {
                minimumFetchIntervalInSeconds = 1 * 60
            }
        }
    }

    override suspend fun getBoolean(key: String): Boolean {
        firebaseRemoteConfig.fetchAndActivate()
        return firebaseRemoteConfig[key]
    }

    override suspend fun getString(key: String): String {
        firebaseRemoteConfig.fetchAndActivate()
        return firebaseRemoteConfig[key]
    }
}
