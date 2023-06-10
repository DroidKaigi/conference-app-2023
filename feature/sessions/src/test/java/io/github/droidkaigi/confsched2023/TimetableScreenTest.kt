package io.github.droidkaigi.confsched2023

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.testing.HiltTestActivity
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import io.github.droidkaigi.confsched2023.testing.robot.TimetableScreenRobot
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
class TimetableScreenTest {

    @get:Rule
    val robotTestRule = RobotTestRule<HiltTestActivity>(this)

    @Inject
    lateinit var timetableScreenRobot: TimetableScreenRobot

    // A screenshot test
    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchShot() {
        timetableScreenRobot(robotTestRule) {
            setupTimetableScreenContent()
            checkScreenCapture()
        }
    }

    // An assertion test for an important feature
    @Test
    fun checkLaunch() {
        timetableScreenRobot(robotTestRule) {
            setupTimetableScreenContent()
            checkTimetableItemsDisplayed()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkFavoriteToggleShot() {
        timetableScreenRobot(robotTestRule) {
            setupTimetableScreenContent()
            clickFirstSessionFavorite()
            checkTimetableListCapture()
            clickFirstSessionFavorite()
            checkTimetableListCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkFavoriteFilterToggleShot() {
        timetableScreenRobot(robotTestRule) {
            setupTimetableScreenContent()
            clickFilter()
            checkTimetableListCapture()
            clickFilter()
            clickFirstSessionFavorite()
            clickFilter()
            checkTimetableListCapture()
        }
    }
}
