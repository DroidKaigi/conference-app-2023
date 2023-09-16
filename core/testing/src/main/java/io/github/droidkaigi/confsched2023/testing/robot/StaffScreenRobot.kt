package io.github.droidkaigi.confsched2023.testing.robot

import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.droidkaigi.confsched2023.data.sessions.FakeSessionsApiClient
import io.github.droidkaigi.confsched2023.data.sessions.SessionsApiClient
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.staff.StaffScreen
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.coroutines.runTestWithLogging
import kotlinx.coroutines.test.TestDispatcher
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class StaffScreenRobot @Inject constructor(
    private val testDispatcher: TestDispatcher,
) {

    @Inject lateinit var robotTestRule: RobotTestRule

    @Inject lateinit var sessionsApiClient: SessionsApiClient
    val fakeSessionsApiClient: FakeSessionsApiClient
        get() = sessionsApiClient as FakeSessionsApiClient
    private lateinit var composeTestRule: AndroidComposeTestRule<*, *>

    operator fun invoke(
        block: StaffScreenRobot.() -> Unit,
    ) {
        runTestWithLogging(timeout = 30.seconds) {
            this@StaffScreenRobot.composeTestRule = robotTestRule.composeTestRule
            block()
        }
    }

    fun setupScreenContent() {
        composeTestRule.setContent {
            KaigiTheme {
                StaffScreen(
                    onBackClick = { },
                    onStaffClick = {},
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
