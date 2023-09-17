package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.achievements.AchievementsScreen
import io.github.droidkaigi.confsched2023.achievements.component.AchievementImageATestTag
import io.github.droidkaigi.confsched2023.achievements.component.AchievementImageBTestTag
import io.github.droidkaigi.confsched2023.achievements.component.AchievementImageCTestTag
import io.github.droidkaigi.confsched2023.achievements.component.AchievementImageDTestTag
import io.github.droidkaigi.confsched2023.achievements.component.AchievementImageETestTag
import io.github.droidkaigi.confsched2023.data.achievements.AchievementsDataStore
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.model.Achievement
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.coroutines.runTestWithLogging
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class AchievementsScreenRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {
    @Inject lateinit var robotTestRule: RobotTestRule
    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>

    @Inject lateinit var achievementsDataStore: AchievementsDataStore

    operator fun invoke(
        block: AchievementsScreenRobot.() -> Unit,
    ) {
        runTestWithLogging(timeout = 30.seconds) {
            this@AchievementsScreenRobot.composeTestRule = robotTestRule.composeTestRule
            block()
        }
    }

    fun setupScreenContent() {
        composeTestRule.setContent {
            KaigiTheme {
                AchievementsScreen()
            }
        }
        waitUntilIdle()
    }

    fun scroll() {
        composeTestRule
            .onAllNodes(isRoot())
            .onFirst()
            .performTouchInput {
                swipeUp(
                    startY = visibleSize.height * 3F / 4,
                    endY = visibleSize.height / 2F,
                )
            }
    }

    fun setupSavedAchievement(achievement: Achievement) = runTest(testDispatcher) {
        achievementsDataStore.saveAchievements(achievement)
    }

    fun clickAchievementImageA() {
        composeTestRule.onNode(hasTestTag(AchievementImageATestTag))
            .performClick()
    }

    fun clickAchievementImageB() {
        composeTestRule.onNode(hasTestTag(AchievementImageBTestTag))
            .performClick()
    }

    fun clickAchievementImageC() {
        composeTestRule.onNode(hasTestTag(AchievementImageCTestTag))
            .performClick()
    }

    fun clickAchievementImageD() {
        composeTestRule.onNode(hasTestTag(AchievementImageDTestTag))
            .performClick()
    }

    fun clickAchievementImageE() {
        composeTestRule.onNode(hasTestTag(AchievementImageETestTag))
            .performClick()
    }

    fun checkScreenCapture() {
        composeTestRule
            .onAllNodes(isRoot())
            .onFirst()
            .captureRoboImage()
    }

    fun waitUntilIdle() {
        composeTestRule.waitForIdle()
        testDispatcher.scheduler.advanceUntilIdle()
    }
}
