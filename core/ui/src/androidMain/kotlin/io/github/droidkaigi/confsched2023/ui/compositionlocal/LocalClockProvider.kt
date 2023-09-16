package io.github.droidkaigi.confsched2023.ui.compositionlocal

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.datetime.Clock

@Suppress("CompositionLocalAllowList")
val LocalClockProvider = staticCompositionLocalOf<Clock> {
    Clock.System
}
