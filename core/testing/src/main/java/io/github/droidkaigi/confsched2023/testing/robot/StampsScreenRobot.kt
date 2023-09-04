package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.stamps.StampsScreen
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.coroutines.runTestWithLogging
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class StampsScreenRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {
    @Inject lateinit var robotTestRule: RobotTestRule
    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>
    operator fun invoke(
        block: StampsScreenRobot.() -> Unit,
    ) {
        runTestWithLogging(timeout = 30.seconds) {
            this@StampsScreenRobot.composeTestRule = robotTestRule.composeTestRule
            block()
        }
    }

    fun setupScreenContent() {
        composeTestRule.setContent {
            KaigiTheme {
                StampsScreen()
            }
        }
        waitUntilIdle()
    }

    fun scroll() {
        composeTestRule
            .onRoot()
            .performTouchInput {
                swipeUp(
                    startY = visibleSize.height * 3F / 4,
                    endY = visibleSize.height / 2F,
                )
            }
    }

    fun checkScreenCapture() {
        composeTestRule
            .onNode(isRoot())
            .captureRoboImage()
    }

    fun waitUntilIdle() {
        composeTestRule.waitForIdle()
        testDispatcher.scheduler.advanceUntilIdle()
    }
}
