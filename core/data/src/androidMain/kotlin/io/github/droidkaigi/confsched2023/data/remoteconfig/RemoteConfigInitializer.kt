package io.github.droidkaigi.confsched2023.data.remoteconfig

import dev.gitlive.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigInitializer @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
) {
    suspend fun initialize() {
        firebaseRemoteConfig.settings {
            minimumFetchIntervalInSeconds = 1 * 60 // 1 hour in seconds
        }
    }
}
