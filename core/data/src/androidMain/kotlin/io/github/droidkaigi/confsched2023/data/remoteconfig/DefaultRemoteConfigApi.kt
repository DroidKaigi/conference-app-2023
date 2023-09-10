package io.github.droidkaigi.confsched2023.data.remoteconfig

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import co.touchlab.kermit.Logger
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.ConfigUpdateListenerRegistration
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.get
import dev.gitlive.firebase.remoteconfig.remoteConfig

class DefaultRemoteConfigApi(lifecycle: Lifecycle) : RemoteConfigApi {

    private val firebaseRemoteConfig = Firebase.remoteConfig

    init {
        lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                var addOnConfigUpdateListener: ConfigUpdateListenerRegistration? = null
                override fun onStart(owner: LifecycleOwner) {
                    addOnConfigUpdateListener =
                        firebaseRemoteConfig.android.addOnConfigUpdateListener(object :
                            ConfigUpdateListener {
                            override fun onUpdate(configUpdate: ConfigUpdate) {
                            }

                            override fun onError(error: FirebaseRemoteConfigException) {
                            }
                        })
                }

                override fun onStop(owner: LifecycleOwner) {
                    addOnConfigUpdateListener?.remove()
                }
            },
        )
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
