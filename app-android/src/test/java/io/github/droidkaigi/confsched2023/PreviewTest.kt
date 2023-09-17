package io.github.droidkaigi.confsched2023

import android.content.res.Configuration
import android.os.LocaleList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import com.airbnb.android.showkase.models.Showkase
import com.airbnb.android.showkase.models.ShowkaseBrowserComponent
import com.github.takahirom.roborazzi.DEFAULT_ROBORAZZI_OUTPUT_DIR_PATH
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiLanguagePreviewDefinition
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviewDefinition
import io.github.droidkaigi.confsched2023.designsystem.preview.ShowkaseMultiplePreviewsWorkaround
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.util.Locale

@RunWith(ParameterizedRobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    qualifiers = RobolectricDeviceQualifiers.NexusOne,
)
class PreviewTest(
    val showkaseBrowserComponent: ShowkaseBrowserComponent,
) {

    @Test
    @Category(ScreenshotTests::class)
    fun previewScreenshot() {
        val filePath =
            DEFAULT_ROBORAZZI_OUTPUT_DIR_PATH + "/" + showkaseBrowserComponent.componentKey + ".png"
        captureRoboImage(
            filePath,
        ) {
            ProvidesPreviewValues(group = showkaseBrowserComponent.group, componentKey = showkaseBrowserComponent.componentKey) {
                showkaseBrowserComponent.component()
            }
        }
    }

    @Suppress("TestFunctionName")
    @ShowkaseMultiplePreviewsWorkaround
    @Composable
    private fun ProvidesPreviewValues(group: String, componentKey: String, content: @Composable () -> Unit) {
        val appliers = arrayListOf<(Configuration) -> Unit>()

        if (isCustomGroup(group = group)) {
            val previewValue = extractPreviewValues(group = group, componentKey)

            when (group) {
                MultiLanguagePreviewDefinition.Group -> {
                    appliers += { c ->
                        c.setLocales(newLocales(baseLocales = c.locales, previewValue = previewValue))
                    }
                }
                MultiThemePreviewDefinition.Group -> {
                    appliers += { c ->
                        c.uiMode = newUiMode(baseUiMode = c.uiMode, previewValue = previewValue)
                    }
                }
            }
        }

        val newConfiguration = appliers.fold(LocalConfiguration.current) { c, a -> c.apply(a) }

        CompositionLocalProvider(LocalConfiguration provides newConfiguration) {
            // Notify locale changes to lang() through the following invocation.
            LocaleList.setDefault(LocalConfiguration.current.locales)

            content()
        }
    }

    /**
     * Depends on the naming rule from Showkase.
     * We must not include "_${group}_" in an original preview function name.
     */
    @ShowkaseMultiplePreviewsWorkaround
    private fun extractPreviewValues(group: String, componentKey: String): String {
        val components = componentKey.split("_")

        // _${group_ is expected here
        val groupIndex = requireNotNull(components.indexOf(group).takeIf { it > 0 }) {
            "Failed to extract a preview value for $group: $group is not found in $components"
        }

        val modifiedPreviewName = requireNotNull(components.getOrNull(groupIndex + 1)) {
            "Failed to extract a preview value for $group: $components is unexpectedly aligned"
        }

        // ${preview_name}_${preview_value}_${...others}
        val match = requireNotNull(Regex("\\w+-([\\w-]+)-[_\\w]+").matchEntire(modifiedPreviewName)) {
            "Failed to extract a preview value for $group: no value was found in $modifiedPreviewName"
        }

        return requireNotNull(match.groupValues.getOrNull(1)) {
            "Failed to extract a preview value for $group: this may be a development issue"
        }
    }

    @ShowkaseMultiplePreviewsWorkaround
    private fun newLocales(baseLocales: LocaleList, previewValue: String): LocaleList {
        val locale = when (previewValue) {
            MultiLanguagePreviewDefinition.English.Name -> {
                MultiLanguagePreviewDefinition.English.Locale
            }
            MultiLanguagePreviewDefinition.Japanese.Name -> {
                MultiLanguagePreviewDefinition.Japanese.Locale
            }
            else -> return baseLocales
        }

        return LocaleList(Locale.getAvailableLocales().first { it.toString() == locale })
    }

    @ShowkaseMultiplePreviewsWorkaround
    private fun newUiMode(baseUiMode: Int, previewValue: String): Int {
        val nightMode = when (previewValue) {
            MultiThemePreviewDefinition.DarkMode.Name -> {
                MultiThemePreviewDefinition.DarkMode.UiMode
            }
            MultiThemePreviewDefinition.LightMode.Name -> {
                MultiThemePreviewDefinition.LightMode.UiMode
            }
            else -> baseUiMode
        }

        val currentNightMode = baseUiMode and Configuration.UI_MODE_NIGHT_MASK
        return baseUiMode xor currentNightMode or nightMode
    }

    companion object {
        fun isCustomGroup(group: String): Boolean {
            return group != "Default Group"
        }

        @ParameterizedRobolectricTestRunner.Parameters
        @JvmStatic
        fun components(): Iterable<Array<Any?>> {
            return Showkase.getMetadata().componentList.map { showkaseBrowserComponent ->
                arrayOf(showkaseBrowserComponent)
            }
        }
    }
}
