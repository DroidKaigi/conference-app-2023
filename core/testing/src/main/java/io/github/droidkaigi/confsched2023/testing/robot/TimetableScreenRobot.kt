package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.data.sessions.FakeSessionsApiClient
import io.github.droidkaigi.confsched2023.data.sessions.SessionsApiClient
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.sessions.TimetableScreen
import io.github.droidkaigi.confsched2023.sessions.TimetableScreenTestTag
import io.github.droidkaigi.confsched2023.sessions.component.SearchButtonTestTag
import io.github.droidkaigi.confsched2023.sessions.component.TimetableBookmarksIconTestTag
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItemBookmarkIconTestTag
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItemTestTag
import io.github.droidkaigi.confsched2023.sessions.component.TimetableUiTypeChangeButtonTestTag
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.coroutines.runTestWithLogging
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class TimetableScreenRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {
    @Inject lateinit var robotTestRule: RobotTestRule

    @Inject lateinit var sessionsApiClient: SessionsApiClient
    val fakeSessionsApiClient: FakeSessionsApiClient
        get() = sessionsApiClient as FakeSessionsApiClient
    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>

    operator fun invoke(
        block: TimetableScreenRobot.() -> Unit,
    ) {
        runTestWithLogging(timeout = 30.seconds) {
            this@TimetableScreenRobot.composeTestRule = robotTestRule.composeTestRule
            block()
        }
    }

    fun setupTimetableScreenContent() {
        composeTestRule.setContent {
            KaigiTheme {
                TimetableScreen(
                    onSearchClick = { },
                    onTimetableItemClick = { },
                    onBookmarkIconClick = { },
                )
            }
        }
        waitUntilIdle()
    }

    enum class ServerStatus {
        Operational,
        Error,
    }

    fun setupServer(serverStatus: ServerStatus) {
        fakeSessionsApiClient.setup(
            when (serverStatus) {
                ServerStatus.Operational -> FakeSessionsApiClient.Status.Operational
                ServerStatus.Error -> FakeSessionsApiClient.Status.Error
            },
        )
    }

    fun clickFirstSession() {
        composeTestRule
            .onAllNodes(hasTestTag(TimetableListItemTestTag))
            .onFirst()
            .performClick()
        waitUntilIdle()
    }

    fun clickFirstSessionBookmark() {
        composeTestRule
            .onAllNodes(hasTestTag(TimetableListItemBookmarkIconTestTag))
            .onFirst()
            .performClick()
        waitUntilIdle()
    }

    fun clickSearchButton() {
        composeTestRule
            .onNode(hasTestTag(SearchButtonTestTag))
            .performClick()
    }

    fun clickTimetableUiTypeChangeButton() {
        composeTestRule
            .onNode(hasTestTag(TimetableUiTypeChangeButtonTestTag))
            .performClick()
    }

    fun clickBookmarkButton() {
        composeTestRule
            .onNode(hasTestTag(TimetableBookmarksIconTestTag))
            .performClick()
    }

    fun scrollTimetable() {
        composeTestRule
            .onNode(hasTestTag(TimetableScreenTestTag))
            .performTouchInput {
                swipeUp(
                    startY = visibleSize.height * 3F / 4,
                    endY = visibleSize.height / 2F,
                )
            }
    }

    fun checkTimetableItemsDisplayed() {
        composeTestRule
            .onAllNodes(hasTestTag(TimetableListItemTestTag))
            .onFirst()
            .assertIsDisplayed()
    }

    fun checkScreenCapture() {
        composeTestRule
            .onNode(isRoot())
            .captureRoboImage()
    }

    fun checkTimetableListCapture() {
        composeTestRule
            .onNode(hasTestTag(TimetableScreenTestTag))
            .captureRoboImage()
    }

    fun waitUntilIdle() {
        composeTestRule.waitForIdle()
        testDispatcher.scheduler.advanceUntilIdle()
    }
}
