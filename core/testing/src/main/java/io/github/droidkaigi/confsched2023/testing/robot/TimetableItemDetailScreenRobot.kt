package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.github.takahirom.roborazzi.Dump
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.data.sessions.fake
import io.github.droidkaigi.confsched2023.data.sessions.response.SessionsAllResponse
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.sessions.TimetableItemDetailScreen
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailBookmarkIconTestTag
import io.github.droidkaigi.confsched2023.sessions.component.TimetableItemDetailReadMoreButtonTestTag
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.coroutines.runTestWithLogging
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class TimetableItemDetailScreenRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {
    @Inject lateinit var robotTestRule: RobotTestRule
    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>
    operator fun invoke(
        block: TimetableItemDetailScreenRobot.() -> Unit,
    ) {
        runTestWithLogging(timeout = 30.seconds) {
            this@TimetableItemDetailScreenRobot.composeTestRule = robotTestRule.composeTestRule
            block()
        }
    }

    fun setupScreenContent() {
        composeTestRule.setContent {
            KaigiTheme {
                TimetableItemDetailScreen(
                    onNavigationIconClick = { },
                    onLinkClick = { },
                    onCalendarRegistrationClick = { },
                    onNavigateToBookmarkScreenRequested = { },
                )
            }
        }
        waitUntilIdle()
    }

    fun clickBookmarkButton() {
        composeTestRule
            .onNode(hasTestTag(TimetableItemDetailBookmarkIconTestTag))
            .performClick()
        waitUntilIdle()
    }

    fun scrollToDescription() {
        composeTestRule
            .onNode(hasTestTag(TimetableItemDetailReadMoreButtonTestTag))
            .performScrollTo()
        scroll()
    }

    fun clickReadMoreButton() {
        composeTestRule
            .onNode(hasTestTag(TimetableItemDetailReadMoreButtonTestTag))
            .performClick()
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

    fun checkAccessibilityCapture() {
        composeTestRule
            .onRoot()
            .captureRoboImage(
                roborazziOptions = RoborazziOptions(
                    captureType = RoborazziOptions.CaptureType.Dump(
                        explanation = Dump.AccessibilityExplanation,
                    ),
                ),
            )
    }

    fun waitUntilIdle() {
        composeTestRule.waitForIdle()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    companion object {
        val defaultSessionId: String = SessionsAllResponse.fake().sessions.find { it.sessionType == "NORMAL" }!!.id
    }
}
