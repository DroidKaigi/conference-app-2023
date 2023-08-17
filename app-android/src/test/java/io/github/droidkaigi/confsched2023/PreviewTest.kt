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
        val components = componentKey.split("-")

        val appliers = arrayListOf<(Configuration) -> Unit>()

        when (group) {
            MultiLanguagePreviewDefinition.Group -> {
                appliers += { c ->
                    c.setLocales(newLocales(baseLocales = c.locales, components = components))
                }
            }
            MultiThemePreviewDefinition.Group -> {
                appliers += { c ->
                    c.uiMode = newUiMode(baseUiMode = c.uiMode, components = components)
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

    @ShowkaseMultiplePreviewsWorkaround
    private fun newLocales(baseLocales: LocaleList, components: List<String>): LocaleList {
        val locale = when {
            MultiLanguagePreviewDefinition.English.Name in components -> {
                MultiLanguagePreviewDefinition.English.Locale
            }
            MultiLanguagePreviewDefinition.Japanese.Name in components -> {
                MultiLanguagePreviewDefinition.Japanese.Locale
            }
            else -> return baseLocales
        }

        return LocaleList(Locale.getAvailableLocales().first { it.toString() == locale })
    }

    @ShowkaseMultiplePreviewsWorkaround
    private fun newUiMode(baseUiMode: Int, components: List<String>): Int {
        val nightMode = when {
            MultiThemePreviewDefinition.DarkMode.Name in components -> {
                MultiThemePreviewDefinition.DarkMode.UiMode
            }
            MultiThemePreviewDefinition.LightMode.Name in components -> {
                MultiThemePreviewDefinition.LightMode.UiMode
            }
            else -> baseUiMode
        }

        val currentNightMode = baseUiMode and Configuration.UI_MODE_NIGHT_MASK
        return baseUiMode xor currentNightMode or nightMode
    }

    companion object {

        @ParameterizedRobolectricTestRunner.Parameters
        @JvmStatic
        fun components(): Iterable<Array<Any?>> {
            return Showkase.getMetadata().componentList.map { showkaseBrowserComponent ->
                arrayOf(showkaseBrowserComponent)
            }
        }
    }
}
