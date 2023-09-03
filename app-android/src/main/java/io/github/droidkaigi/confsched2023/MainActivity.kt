package io.github.droidkaigi.confsched2023

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.window.layout.DisplayFeature
import androidx.window.layout.WindowInfoTracker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        // Navigation icon color can be changed since API 26(O)
        if (VERSION.SDK_INT < VERSION_CODES.O) {
            enableEdgeToEdge()
        } else {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    lightScrim = Color.Transparent.toArgb(),
                    darkScrim = Color.Transparent.toArgb(),
                ),
                navigationBarStyle = SystemBarStyle.auto(
                    lightScrim = Color.Transparent.toArgb(),
                    darkScrim = Color.Transparent.toArgb(),
                ),
            )

            // For API29(Q) or higher and 3-button navigation,
            // the following code must be written to make the navigation color completely transparent.
            if (VERSION.SDK_INT >= VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
        }

        setContent {
            val windowSize = calculateWindowSizeClass(this)
            val displayFeatures = calculateDisplayFeatures(this)
            KaigiApp(
                windowSize = windowSize,
                displayFeatures = displayFeatures,
            )
        }
    }
}

@Composable
private fun calculateDisplayFeatures(activity: ComponentActivity): PersistentList<DisplayFeature> {
    val windowLayoutInfo = remember(activity) {
        WindowInfoTracker.getOrCreate(activity).windowLayoutInfo(activity)
    }
    val displayFeatures by produceState(
        initialValue = persistentListOf(),
        key1 = windowLayoutInfo,
    ) {
        windowLayoutInfo.collect { info ->
            value = info.displayFeatures.toPersistentList()
        }
    }

    return displayFeatures
}
