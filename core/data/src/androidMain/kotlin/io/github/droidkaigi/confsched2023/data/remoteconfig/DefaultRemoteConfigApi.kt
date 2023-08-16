package io.github.droidkaigi.confsched2023.data.remoteconfig

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.get
import dev.gitlive.firebase.remoteconfig.remoteConfig

actual class DefaultRemoteConfigApi : RemoteConfigApi {

    private val firebaseRemoteConfig = Firebase.remoteConfig

    /**
     * If you want to change the interval time to fetch, please change it here
     */
//    init {
//        CoroutineScope(Dispatchers.IO).launch {
//            firebaseRemoteConfig.settings {
//                minimumFetchIntervalInSeconds = 12 * 3600L
//            }
//        }
//    }

    actual override suspend fun getBoolean(key: String): Boolean {
        firebaseRemoteConfig.fetchAndActivate()
        return firebaseRemoteConfig[key]
    }
}
