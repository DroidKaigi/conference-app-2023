package io.github.droidkaigi.confsched2023

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.testing.HiltTestActivity
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import io.github.droidkaigi.confsched2023.testing.robot.ContributorsScreenRobot
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
class ContributorsScreenTest {

    @get:Rule
    @BindValue val robotTestRule: RobotTestRule = RobotTestRule<HiltTestActivity>(this)

    @Inject
    lateinit var contributorsScreenRobot: ContributorsScreenRobot

    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchShot() {
        contributorsScreenRobot {
            setupScreenContent()
            checkScreenCapture()
            scroll()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    @Config(fontScale = 0.5f)
    fun checkSmallFontScaleShot() {
        contributorsScreenRobot {
            setupScreenContent()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    @Config(fontScale = 1.5f)
    fun checkLargeFontScaleShot() {
        contributorsScreenRobot {
            setupScreenContent()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    @Config(fontScale = 2.0f)
    fun checkHugeFontScaleShot() {
        contributorsScreenRobot {
            setupScreenContent()
            checkScreenCapture()
        }
    }
}
