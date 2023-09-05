package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.main.MainScreenTab
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.coroutines.runTestWithLogging
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class KaigiAppRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {

    @Inject lateinit var robotTestRule: RobotTestRule

    @Inject lateinit var timetableScreenRobot: TimetableScreenRobot

    @Inject lateinit var aboutScreenRobot: AboutScreenRobot
    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>
    operator fun invoke(
        block: KaigiAppRobot.() -> Unit,
    ) {
        runTestWithLogging(timeout = 30.seconds) {
            this@KaigiAppRobot.composeTestRule = robotTestRule.composeTestRule
            waitUntilIdle()
            block()
        }
    }

    fun capture() {
        composeTestRule
            .onNode(isRoot())
            .captureRoboImage()
    }

    // FIXME I am doing this because capture() does not take a screenshot of the Achievement screen.
    // If you know how to fix it, please fix it.
    fun captureWhenAchievementScreen() {
        composeTestRule
            .onAllNodes(isRoot())
            .onFirst()
            .captureRoboImage()
    }

    fun goToAbout() {
        composeTestRule
            .onNode(hasTestTag(MainScreenTab.About.testTag))
            .performClick()
        waitUntilIdle()
    }

    fun goToFloorMap() {
        composeTestRule
            .onNode(hasTestTag(MainScreenTab.FloorMap.testTag))
            .performClick()
        waitUntilIdle()
    }

    fun goToAchievements() {
        composeTestRule
            .onAllNodes(
                matcher = hasTestTag(MainScreenTab.Achievements.testTag),
                useUnmergedTree = true,
            )
            .onFirst()
            .performClick()
        waitUntilIdle()
    }

    fun waitUntilIdle() {
        composeTestRule.waitForIdle()
        testDispatcher.scheduler.advanceUntilIdle()
    }
}
