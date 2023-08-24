package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.performClick
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.data.sessions.SessionsApiClient
import io.github.droidkaigi.confsched2023.data.sessions.fake
import io.github.droidkaigi.confsched2023.data.sessions.response.SessionsAllResponse
import io.github.droidkaigi.confsched2023.data.sessions.toTimetable
import io.github.droidkaigi.confsched2023.data.user.UserDataStore
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.sessions.BookmarkScreen
import io.github.droidkaigi.confsched2023.sessions.component.BookmarkFilterChipAllTestTag
import io.github.droidkaigi.confsched2023.sessions.component.BookmarkFilterChipDay1TestTag
import io.github.droidkaigi.confsched2023.sessions.component.BookmarkFilterChipDay2TestTag
import io.github.droidkaigi.confsched2023.sessions.component.BookmarkFilterChipDay3TestTag
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.coroutines.runTestWithLogging
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class BookmarkScreenRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {
    @Inject lateinit var robotTestRule: RobotTestRule

    @Inject lateinit var sessionsApiClient: SessionsApiClient

    @Inject lateinit var userDataStore: UserDataStore

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

    fun clickBookmarkFilterChipAll() {
        composeTestRule
            .onNode(hasTestTag(BookmarkFilterChipAllTestTag))
            .performClick()
    }

    fun clickBookmarkFilterChipDay1() {
        composeTestRule
            .onNode(hasTestTag(BookmarkFilterChipDay1TestTag))
            .performClick()
    }

    fun clickBookmarkFilterChipDay2() {
        composeTestRule
            .onNode(hasTestTag(BookmarkFilterChipDay2TestTag))
            .performClick()
    }

    fun clickBookmarkFilterChipDay3() {
        composeTestRule
            .onNode(hasTestTag(BookmarkFilterChipDay3TestTag))
            .performClick()
    }

    fun toggleFavorite() = runTest(testDispatcher) {
        SessionsAllResponse.fake().toTimetable().bookmarks.forEach {
            userDataStore.toggleFavorite(it)
        }
    }

    fun waitUntilIdle() {
        composeTestRule.waitForIdle()
        testDispatcher.scheduler.advanceUntilIdle()
    }
}
