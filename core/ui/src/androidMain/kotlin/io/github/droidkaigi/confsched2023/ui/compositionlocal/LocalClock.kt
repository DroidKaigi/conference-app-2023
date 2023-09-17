package io.github.droidkaigi.confsched2023.ui.compositionlocal

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Suppress("CompositionLocalAllowlist")
val LocalClock = staticCompositionLocalOf<Clock> {
    Clock.System
}

object FakeClock : Clock {
    override fun now(): Instant = Instant.parse("2023-09-14T10:00:00.000Z")
}
