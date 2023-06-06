package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.sessions.TimetableScreen
import io.github.droidkaigi.confsched2023.sessions.component.TimetableListItemTestTag
import io.github.droidkaigi.confsched2023.sessions.section.TimetableContentTestTag
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import javax.inject.Inject

class TimetableScreenRobot @Inject constructor() {

    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>
    operator fun invoke(
        robotTestRule: RobotTestRule<*>,
        block: TimetableScreenRobot.() -> Unit
    ) {
        this.composeTestRule = robotTestRule.composeTestRule
        block()
    }

    fun setTimetableScreenContent() {
        composeTestRule.setContent {
            TimetableScreen(
                onContributorsClick = { }
            )
        }
    }

    fun clickFirstSessionFavorite() {
        composeTestRule
            .onAllNodes(hasText("☆"))
            .onFirst()
            .performClick()
    }

    fun clickFilter() {
        composeTestRule
            .onAllNodes(hasTestTag(TimetableContentTestTag))
            .onFirst()
            .performClick()
    }

    fun checkTimetableItemsDisplayed() {
        composeTestRule
            .onAllNodes(hasTestTag(TimetableListItemTestTag))
            .onFirst()
            .assertIsDisplayed()
    }

    fun checkCaptureScreen() {
        composeTestRule
            .onNode(isRoot())
            .captureRoboImage()
    }

    fun checkCaptureTimetableContent() {
        composeTestRule
            .onNode(hasTestTag(TimetableContentTestTag))
            .captureRoboImage()
    }
}
