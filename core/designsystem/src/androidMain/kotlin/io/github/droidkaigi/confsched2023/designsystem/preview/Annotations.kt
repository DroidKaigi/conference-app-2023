package io.github.droidkaigi.confsched2023.designsystem.preview

import android.content.res.Configuration
import androidx.annotation.VisibleForTesting
import androidx.compose.ui.tooling.preview.Preview
import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION

/**
 * Annotate workaround entities for https://github.com/DroidKaigi/conference-app-2023/issues/627
 */
@VisibleForTesting
@Retention(SOURCE)
@Target(CLASS, FUNCTION)
annotation class ShowkaseMultiplePreviewsWorkaround

/**
 * Definitions for MultiThemePreviews
 */
@VisibleForTesting
@ShowkaseMultiplePreviewsWorkaround
object MultiThemePreviewDefinition {
    const val Group = "Theme"

    object DarkMode {
        const val Name = "DarkMode"
        const val UiMode = Configuration.UI_MODE_NIGHT_YES
    }

    object LightMode {
        const val Name = "LightMode"
        const val UiMode = Configuration.UI_MODE_NIGHT_NO
    }
}

/**
 * Annotation for previewing multiple themes.
 */
@Preview(
    name = MultiThemePreviewDefinition.LightMode.Name,
    group = MultiThemePreviewDefinition.Group,
    uiMode = MultiThemePreviewDefinition.LightMode.UiMode,
)
@Preview(
    name = MultiThemePreviewDefinition.DarkMode.Name,
    group = MultiThemePreviewDefinition.Group,
    uiMode = MultiThemePreviewDefinition.DarkMode.UiMode,
)
annotation class MultiThemePreviews

/**
 * Definitions for MultiLanguagePreviews
 */
@VisibleForTesting
@ShowkaseMultiplePreviewsWorkaround
object MultiLanguagePreviewDefinition {
    const val Group = "Language"

    object Japanese {
        const val Name = "Japanese"
        const val Locale = "ja_JP"
        const val LocaleShort = "ja"
    }

    object English {
        const val Name = "English"
        const val Locale = "en_US"
        const val LocaleShort = "en"
    }
}

/**
 * Annotation for previewing multiple languages.
 *
 * Note: locale param need to follow [locale qualifier](https://developer.android.com/guide/topics/resources/providing-resources#LocaleQualifier).
 */
@Preview(
    name = MultiLanguagePreviewDefinition.Japanese.Name,
    group = MultiLanguagePreviewDefinition.Group,
    locale = MultiLanguagePreviewDefinition.Japanese.LocaleShort,
)
@Preview(
    name = MultiLanguagePreviewDefinition.English.Name,
    group = MultiLanguagePreviewDefinition.Group,
    locale = MultiLanguagePreviewDefinition.English.LocaleShort,
)
annotation class MultiLanguagePreviews
