package io.github.droidkaigi.confsched2023

import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.testing.HiltAndroidAutoInjectRule
import javax.inject.Inject
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@HiltAndroidTest
@Config(
    qualifiers = RobolectricDeviceQualifiers.Pixel6
)
class KaigiScreenshotTest {

    @get:Rule val hiltAutoInjectrule = HiltAndroidAutoInjectRule(this)
    @get:Rule val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject lateinit var KaigiScreenRobot: KaigiScreenRobot

    @Test
    fun startup() {
        KaigiScreenRobot(composeTestRule) {
            capture()
        }
    }

    @Test
    fun navigateToContributor() {
        KaigiScreenRobot(composeTestRule) {
            capture()
            goToContributor()
            capture()
        }
    }
}

class KaigiScreenRobot @Inject constructor() {

    lateinit var composeTestRule: AndroidComposeTestRule<*, *>
    operator fun invoke(
        composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
        block: KaigiScreenRobot.() -> Unit
    ) {
        this.composeTestRule = composeTestRule
        block()
    }

    fun capture() {
        composeTestRule
            .onNode(isRoot())
            .captureRoboImage()
    }

    fun goToContributor() {
        composeTestRule
            .onNodeWithText("Go to ContributorsScreen")
            .performClick()
    }
}
