package io.github.droidkaigi.confsched2023.sessions

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.model.AppVersion
import io.github.droidkaigi.confsched2023.testing.HiltTestActivity
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import io.github.droidkaigi.confsched2023.testing.robot.TimetableScreenRobot
import io.github.droidkaigi.confsched2023.testing.robot.TimetableScreenRobot.ServerStatus
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
class TimetableScreenTest {

    @get:Rule
    @BindValue val robotTestRule: RobotTestRule = RobotTestRule<HiltTestActivity>(this)

    @Inject
    lateinit var timetableScreenRobot: TimetableScreenRobot

    @BindValue val appVersion: AppVersion = AppVersion.fake()

    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchShot() {
        timetableScreenRobot {
            setupTimetableScreenContent()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchServerErrorShot() {
        timetableScreenRobot {
            setupServer(ServerStatus.Error)
            setupTimetableScreenContent()
            checkScreenCapture()
        }
    }

    @Test
    fun checkLaunch() {
        timetableScreenRobot {
            setupTimetableScreenContent()
            checkTimetableItemsDisplayed()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchAccessibilityShot() {
        timetableScreenRobot {
            setupTimetableScreenContent()
            checkAccessibilityCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkBookmarkToggleShot() {
        timetableScreenRobot {
            setupTimetableScreenContent()
            clickFirstSessionBookmark()
            checkTimetableListCapture()
            clickFirstSessionBookmark()
            checkTimetableListCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkScrollShot() {
        timetableScreenRobot {
            setupTimetableScreenContent()
            scrollTimetable()
            checkTimetableListCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkGridShot() {
        timetableScreenRobot {
            setupTimetableScreenContent()
            clickTimetableUiTypeChangeButton()
            checkTimetableListCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkGridScrollShot() {
        timetableScreenRobot {
            setupTimetableScreenContent()
            clickTimetableUiTypeChangeButton()
            scrollTimetable()
            checkTimetableListCapture()
        }
    }
}
