package io.github.droidkaigi.confsched2023

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import io.github.droidkaigi.confsched2023.testing.robot.KaigiAppRobot
import javax.inject.Inject
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@HiltAndroidTest
@Config(
    qualifiers = RobolectricDeviceQualifiers.NexusOne
)
@Category(
    ScreenshotTests::class
)
class KaigiAppTest {

    @get:Rule
    val robotTestRule = RobotTestRule<MainActivity>(this)

    @Inject lateinit var kaigiAppRobot: KaigiAppRobot

    @Test
    fun checkStartupShot() {
        kaigiAppRobot(robotTestRule) {
            capture()
        }
    }

    @Test
    fun checkStartup() {
        kaigiAppRobot(robotTestRule) {
            timetableScreenRobot(robotTestRule) {
                checkTimetableItemsDisplayed()
            }
        }
    }

    @Test
    fun checkNavigateToContributorShot() {
        kaigiAppRobot(robotTestRule) {
            goToContributor()
            capture()
        }
    }
}

