package io.github.droidkaigi.confsched2023.ui.compositionlocal

import androidx.compose.runtime.compositionLocalOf
import kotlinx.datetime.Clock

val LocalClockProvider = compositionLocalOf<Clock> {
    Clock.System
}
