package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.data.sessions.SessionsApiClient
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.sessions.BookmarkScreen
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.coroutines.runTestWithLogging
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class BookmarkScreenRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {
    @Inject lateinit var robotTestRule: RobotTestRule

    @Inject lateinit var sessionsApiClient: SessionsApiClient
    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>

    operator fun invoke(
        block: BookmarkScreenRobot.() -> Unit,
    ) {
        runTestWithLogging(timeout = 30.seconds) {
            this@BookmarkScreenRobot.composeTestRule = robotTestRule.composeTestRule
            block()
        }
    }

    fun setupBookmarkScreenContent() {
        composeTestRule.setContent {
            KaigiTheme {
                BookmarkScreen(
                    onBackPressClick = { },
                    onTimetableItemClick = { },
                )
            }
        }
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
}
