package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.sessions.TimetableScreen
import io.github.droidkaigi.confsched2023.sessions.TimetableScreenTestTag
import io.github.droidkaigi.confsched2023.sessions.component.SearchButtonTestTag
import io.github.droidkaigi.confsched2023.sessions.component.TimetableBookmarkIconTestTag
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItemTestTag
import io.github.droidkaigi.confsched2023.sessions.component.TimetableUiTypeChangeButtonTestTag
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Inject

class TimetableScreenRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {

    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>
    operator fun invoke(
        robotTestRule: RobotTestRule<*>,
        block: TimetableScreenRobot.() -> Unit,
    ) {
        this.composeTestRule = robotTestRule.composeTestRule
        block()
    }

    fun setupTimetableScreenContent() {
        composeTestRule.setContent {
            KaigiTheme {
                TimetableScreen(
                    onSearchClick = { },
                    onTimetableItemClick = { },
                    onClickBookmarkIcon = { },
                )
            }
        }
        waitUntilIdle()
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
            .onAllNodes(hasText("☆"))
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
            .onNode(hasTestTag(TimetableBookmarkIconTestTag))
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
