package io.github.droidkaigi.confsched2023

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
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import javax.inject.Inject
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@HiltAndroidTest
@Config(
    qualifiers = RobolectricDeviceQualifiers.NexusOne
)
class TimetableScreenshotTest {

    @get:Rule
    val robotTestRule = RobotTestRule(this)

    @Inject
    lateinit var timetableScreenRobot: TimetableScreenRobot

    @Test
    fun canLaunch() {
        timetableScreenRobot(robotTestRule) {
            capture()
        }
    }

    @Test
    fun canFavorite() {
        timetableScreenRobot(robotTestRule) {
            capture()
            clickFirstSessionFavorite()
            capture()
            clickFirstSessionFavorite()
            capture()
        }
    }

    @Test
    fun canFavoriteFilter() {
        timetableScreenRobot(robotTestRule) {
            clickFilter()
            capture()
            clickFilter()
            clickFirstSessionFavorite()
            clickFilter()
            capture()
        }
    }
}

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class TimetableScreenTest {

    @get:Rule
    val robotTestRule = RobotTestRule(this)

    @Inject
    lateinit var timetableScreenRobot: TimetableScreenRobot

    @Test
    fun shouldBeAbleToLaunch() {
        timetableScreenRobot(robotTestRule) {
        }
    }
}

// ④ Shared Testing Robot
class TimetableScreenRobot @Inject constructor() {

    lateinit var composeTestRule: AndroidComposeTestRule<*, *>
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

    fun capture() {
        // ③ Capture Robolectric image
        composeTestRule
            .onNode(isRoot())
            .captureRoboImage()
    }
}
