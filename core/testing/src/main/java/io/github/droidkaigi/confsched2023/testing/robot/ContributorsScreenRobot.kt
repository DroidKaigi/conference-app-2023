package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.contributors.ContributorsScreen
import io.github.droidkaigi.confsched2023.contributors.ContributorsViewModel
import io.github.droidkaigi.confsched2023.data.contributors.DefaultContributorsRepository
import io.github.droidkaigi.confsched2023.data.contributors.FakeContributorsApiClient
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.coroutines.runTestWithLogging
import io.github.droidkaigi.confsched2023.ui.UserMessageStateHolderImpl
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class ContributorsScreenRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {

    @Inject lateinit var robotTestRule: RobotTestRule
    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>

    operator fun invoke(
        block: ContributorsScreenRobot.() -> Unit,
    ) {
        runTestWithLogging(timeout = 30.seconds) {
            this@ContributorsScreenRobot.composeTestRule = robotTestRule.composeTestRule
            block()
        }
    }

    fun setupScreenContent() {
        composeTestRule.setContent {
            KaigiTheme {
                ContributorsScreen(
                    viewModel = ContributorsViewModel(
                        contributorsRepository = DefaultContributorsRepository(
                            contributorsApi = FakeContributorsApiClient(),
                        ),
                        userMessageStateHolder = UserMessageStateHolderImpl(),
                    ),
                    onNavigationIconClick = {},
                    onContributorItemClick = {},
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
}
