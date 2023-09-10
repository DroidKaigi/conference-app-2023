package io.github.droidkaigi.confsched2023

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.testing.HiltTestActivity
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import io.github.droidkaigi.confsched2023.testing.robot.FloorMapScreenRobot
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@HiltAndroidTest
@Config(
    qualifiers = RobolectricDeviceQualifiers.NexusOne,
)
class FloorMapScreenTest {

    @get:Rule
    @BindValue val robotTestRule: RobotTestRule = RobotTestRule<HiltTestActivity>(this)

    @Inject
    lateinit var floorMapScreenRobot: FloorMapScreenRobot

    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchShot() {
        floorMapScreenRobot {
            setupScreenContent()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkChangeFloorShot() {
        floorMapScreenRobot {
            setupScreenContent()
            clickFloorGroundButton()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    @Config(fontScale = 0.5f)
    fun smallFontScaleShot() {
        floorMapScreenRobot {
            setupScreenContent()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    @Config(fontScale = 1.5f)
    fun largeFontScaleShot() {
        floorMapScreenRobot {
            setupScreenContent()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    @Config(fontScale = 2.0f)
    fun hugeFontScaleShot() {
        floorMapScreenRobot {
            setupScreenContent()
            checkScreenCapture()
        }
    }
}
