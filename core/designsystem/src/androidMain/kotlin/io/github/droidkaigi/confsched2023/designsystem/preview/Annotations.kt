package io.github.droidkaigi.confsched2023.designsystem.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Annotation for previewing multiple themes.
 */
@Preview(
    name = "Light Mode",
    group = "Theme",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark Mode",
    group = "Theme",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
annotation class MultiThemePreviews

/**
 * Annotation for previewing multiple languages.
 */
@Preview(
    name = "Japanese",
    group = "Language",
    locale = "ja",
)
@Preview(
    name = "English",
    group = "Language",
    locale = "en",
)
annotation class MultiLanguagePreviews
