package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.about.AboutScreen
import io.github.droidkaigi.confsched2023.about.AboutScreenTestTag
import io.github.droidkaigi.confsched2023.about.component.AboutCreditsSponsorsItemTestTag
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.coroutines.runTestWithLogging
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class AboutScreenRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {
    @Inject lateinit var robotTestRule: RobotTestRule
    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>
    operator fun invoke(
        block: AboutScreenRobot.() -> Unit,
    ) {
        runTestWithLogging(timeout = 30.seconds) {
            this@AboutScreenRobot.composeTestRule = robotTestRule.composeTestRule
            block()
        }
    }

    fun setupAboutScreenContent() {
        composeTestRule.setContent {
            KaigiTheme {
                AboutScreen(
                    onAboutItemClick = { },
                    onLinkClick = { },
                )
            }
        }
        waitUntilIdle()
    }

    fun clickSponsors() {
        scrollAboutScreen()
        composeTestRule.onNodeWithTag(AboutCreditsSponsorsItemTestTag)
            .performClick()
        waitUntilIdle()
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

    fun scrollAboutScreen() {
        composeTestRule
            .onNode(hasTestTag(AboutScreenTestTag))
            .performTouchInput {
                swipeUp(
                    startY = visibleSize.height * 3F / 4,
                    endY = visibleSize.height / 3F,
                )
            }
    }
}
