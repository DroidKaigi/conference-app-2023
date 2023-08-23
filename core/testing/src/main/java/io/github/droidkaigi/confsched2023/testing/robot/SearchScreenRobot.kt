package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.sessions.SearchScreen
import io.github.droidkaigi.confsched2023.sessions.SearchScreenEmptyBodyTestTag
import io.github.droidkaigi.confsched2023.sessions.SearchScreenFilterTestTag
import io.github.droidkaigi.confsched2023.sessions.SearchScreenSearchTextFiledTestTag
import io.github.droidkaigi.confsched2023.sessions.SearchScreenTestTag
import io.github.droidkaigi.confsched2023.sessions.component.SearchFilterCategoryChipTestTag
import io.github.droidkaigi.confsched2023.sessions.component.SearchFilterDayFilterChipTestTag
import io.github.droidkaigi.confsched2023.sessions.component.SearchFilterLanguageChipTestTag
import io.github.droidkaigi.confsched2023.sessions.component.SearchFilterSessionTypeChipTestTag
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.coroutines.runTestWithLogging
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class SearchScreenRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {
    @Inject lateinit var robotTestRule: RobotTestRule
    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>

    operator fun invoke(
        block: SearchScreenRobot.() -> Unit,
    ) {
        runTestWithLogging(timeout = 30.seconds) {
            this@SearchScreenRobot.composeTestRule = robotTestRule.composeTestRule
            block()
        }
    }

    fun setupSearchScreenContent() {
        composeTestRule.setContent {
            KaigiTheme {
                SearchScreen(
                    onBackClick = {},
                    onTimetableItemClick = {},
                )
            }
        }
        waitUntilIdle()
    }

    fun waitUntilIdle() {
        composeTestRule.waitForIdle()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    fun scrollSearchFilterHorizontally() {
        composeTestRule
            .onNode(hasTestTag(SearchScreenFilterTestTag))
            .performTouchInput {
                swipeLeft(
                    startX = visibleSize.width * 3F / 4,
                    endX = visibleSize.width / 3F,
                )
            }
    }

    fun inputDummyTextSearchTextFieldAppBar() {
        composeTestRule
            .onNodeWithTag(SearchScreenSearchTextFiledTestTag)
            .performTextInput("abcdefg")
        waitUntilIdle()
    }

    fun checkScreenCapture() {
        composeTestRule
            .onNode(hasTestTag(SearchScreenTestTag))
            .captureRoboImage()
    }

    fun checkExistEmptyBody() {
        composeTestRule
            .onNodeWithTag(SearchScreenEmptyBodyTestTag)
            .captureRoboImage()
    }

    fun clickFilterDayChip() {
        composeTestRule
            .onNodeWithTag(SearchFilterDayFilterChipTestTag)
            .performClick()
        waitUntilIdle()
    }

    fun clickFilterCategoryChip() {
        composeTestRule
            .onNodeWithTag(SearchFilterCategoryChipTestTag)
            .performClick()
        waitUntilIdle()
    }

    fun clickFilterSessionTypeChip() {
        composeTestRule
            .onNodeWithTag(SearchFilterSessionTypeChipTestTag)
            .performClick()
        waitUntilIdle()
    }

    fun clickFilterLanguageChip() {
        composeTestRule
            .onNodeWithTag(SearchFilterLanguageChipTestTag)
            .performClick()
        waitUntilIdle()
    }
}
