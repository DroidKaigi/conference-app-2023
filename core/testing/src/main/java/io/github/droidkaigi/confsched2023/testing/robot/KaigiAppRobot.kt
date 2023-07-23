package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.main.MainScreenTab
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Inject

class KaigiAppRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {

    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>

    @Inject lateinit var timetableScreenRobot: TimetableScreenRobot
    operator fun invoke(
        composeTestRule: RobotTestRule<*>,
        block: KaigiAppRobot.() -> Unit,
    ) {
        this.composeTestRule = composeTestRule.composeTestRule
        waitUntilIdle()
        block()
    }

    fun capture() {
        composeTestRule
            .onNode(isRoot())
            .captureRoboImage()
    }

    fun goToContributor() {
        composeTestRule
            .onNodeWithContentDescription(MainScreenTab.Contributor.contentDescription)
            .performClick()
        waitUntilIdle()
    }

    fun waitUntilIdle() {
        composeTestRule.waitForIdle()
        testDispatcher.scheduler.advanceUntilIdle()
    }
}
