package io.github.droidkaigi.confsched2023

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.droidkaigi.confsched2023.data.remoteconfig.RemoteConfigInitializer
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject lateinit var remoteConfigInitializer: RemoteConfigInitializer

    override fun onCreate() {
        super.onCreate()
        runBlocking {
            remoteConfigInitializer.initialize()
        }
    }
}
