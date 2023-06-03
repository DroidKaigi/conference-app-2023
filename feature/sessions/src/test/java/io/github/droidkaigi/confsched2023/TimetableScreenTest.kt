package io.github.droidkaigi.confsched2023

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.sessions.TimetableScreen
import io.github.droidkaigi.confsched2023.sessions.section.TimeTableContentTestTag
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
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
    val robotTestRule = RobotTestRule(this)

    @Inject
    lateinit var timetableScreenRobot: TimetableScreenRobot

    // A screenshot test
    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchShot() {
        timetableScreenRobot(robotTestRule) {
            checkCaptureScreen()
        }
    }

    // An assertion test for an important feature
    @Test
    fun checkLaunch() {
        timetableScreenRobot(robotTestRule) {
            checkTimetableItemsDisplayed()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkFavoriteToggleShot() {
        timetableScreenRobot(robotTestRule) {
            clickFirstSessionFavorite()
            checkCaptureTimetableContent()
            clickFirstSessionFavorite()
            checkCaptureTimetableContent()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkFavoriteFilterToggleShot() {
        timetableScreenRobot(robotTestRule) {
            clickFilter()
            checkCaptureTimetableContent()
            clickFilter()
            clickFirstSessionFavorite()
            clickFilter()
            checkCaptureTimetableContent()
        }
    }
}

class TimetableScreenRobot @Inject constructor() {

    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>
    operator fun invoke(
        robotTestRule: RobotTestRule,
        block: TimetableScreenRobot.() -> Unit
    ) {
        this.composeTestRule = robotTestRule.composeTestRule
        composeTestRule.setContent {
            TimetableScreen(
                onContributorsClick = { }
            )
        }
        block()
    }

    fun clickFirstSessionFavorite() {
        composeTestRule
            .onAllNodes(hasText("☆"))
            .onFirst()
            .performClick()
    }

    fun clickFilter() {
        composeTestRule
            .onAllNodes(hasTestTag("Filter"))
            .onFirst()
            .performClick()
    }

    fun checkTimetableItemsDisplayed() {
        composeTestRule
            .onAllNodes(hasTestTag("TimetableItem"))
            .onFirst()
            .assertIsDisplayed()
    }

    fun checkCaptureScreen() {
        composeTestRule
            .onNode(isRoot())
            .captureRoboImage()
    }

    fun checkCaptureTimetableContent() {
        composeTestRule
            .onNode(hasTestTag(TimeTableContentTestTag))
            .captureRoboImage()
    }
}
