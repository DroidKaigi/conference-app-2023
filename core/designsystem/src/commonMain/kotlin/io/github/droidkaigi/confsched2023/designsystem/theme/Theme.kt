package io.github.droidkaigi.confsched2023.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Generated code from https://m3.material.io/theme-builder#/custom

private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

sealed class HallColorScheme {
    abstract val hallA: Color
    abstract val hallB: Color
    abstract val hallC: Color
    abstract val hallD: Color
    abstract val hallE: Color
    abstract val hallText: Color
    abstract val hallTextWhenWithoutSpeakers: Color

    data class Light(
        override val hallA: Color = md_theme_light_room_hall_a,
        override val hallB: Color = md_theme_light_room_hall_b,
        override val hallC: Color = md_theme_light_room_hall_c,
        override val hallD: Color = md_theme_light_room_hall_d,
        override val hallE: Color = md_theme_light_room_hall_e,
        override val hallText: Color = md_theme_light_room_hall_text,
        override val hallTextWhenWithoutSpeakers: Color = md_theme_light_onSurfaceVariant,
    ) : HallColorScheme()

    data class Dark(
        override val hallA: Color = md_theme_dark_room_hall_a,
        override val hallB: Color = md_theme_dark_room_hall_b,
        override val hallC: Color = md_theme_dark_room_hall_c,
        override val hallD: Color = md_theme_dark_room_hall_d,
        override val hallE: Color = md_theme_dark_room_hall_e,
        override val hallText: Color = md_theme_dark_room_hall_text,
        override val hallTextWhenWithoutSpeakers: Color = md_theme_dark_onSurfaceVariant,
    ) : HallColorScheme()
}

sealed class FloorButtonColorScheme {
    abstract val background: Color

    data class Light(
        override val background: Color = md_theme_light_floor_button_background,
    ) : FloorButtonColorScheme()

    data class Dark(
        override val background: Color = md_theme_dark_floor_button_background,
    ) : FloorButtonColorScheme()
}

@Composable
fun hallColors() = if (isSystemInDarkTheme()) {
    HallColorScheme.Dark()
} else {
    HallColorScheme.Light()
}

@Composable
fun floorButtonColors() = if (isSystemInDarkTheme()) {
    FloorButtonColorScheme.Dark()
} else {
    FloorButtonColorScheme.Light()
}

@Composable
fun KaigiTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content:
    @Composable()
    () -> Unit,
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography(),
        content = content,
    )
}
