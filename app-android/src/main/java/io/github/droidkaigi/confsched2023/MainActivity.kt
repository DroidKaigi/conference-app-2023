package io.github.droidkaigi.confsched2023

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

        // FIXME
        //  On a device that can switch between 3-button navigation and gesture navigation,
        //  the color of the navigation bar does not match KaigiBottomBar container color
        //  when using 3-button navigation.
        //  Devices with only 3-button navigation do not have the above problem.
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
