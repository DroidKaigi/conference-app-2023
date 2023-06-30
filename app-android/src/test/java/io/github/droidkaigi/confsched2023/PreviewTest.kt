package io.github.droidkaigi.confsched2023

import com.airbnb.android.showkase.models.Showkase
import com.airbnb.android.showkase.models.ShowkaseBrowserComponent
import com.github.takahirom.roborazzi.DEFAULT_ROBORAZZI_OUTPUT_DIR_PATH
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

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
            showkaseBrowserComponent.component()
        }
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
