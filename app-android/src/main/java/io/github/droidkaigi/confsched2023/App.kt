package io.github.droidkaigi.confsched2023

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.droidkaigi.confsched2023.data.di.ServerEnvironmentModule

@HiltAndroidApp
class App : Application(), ServerEnvironmentModule.HasServerEnvironment {
    override val serverEnvironment: ServerEnvironmentModule.ServerEnvironment =
        ServerEnvironmentModule.ServerEnvironment(
            BuildConfig.SERVER_URL,
        )
}
