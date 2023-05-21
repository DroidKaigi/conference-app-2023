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
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import javax.inject.Inject
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@HiltAndroidTest
@Config(
    qualifiers = RobolectricDeviceQualifiers.NexusOne
)
@Category(
    ScreenshotTests::class
)
class KaigiAppTest {

    @get:Rule val hiltAutoInjectrule = HiltAndroidAutoInjectRule(this)
    @get:Rule val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject lateinit var kaigiAppRobot: KaigiAppRobot

    @Test
    fun startup() {
        kaigiAppRobot(composeTestRule) {
            capture()
        }
    }

    @Test
    fun navigateToContributor() {
        kaigiAppRobot(composeTestRule) {
            capture()
            goToContributor()
            capture()
        }
    }
}

class KaigiAppRobot @Inject constructor() {

    lateinit var composeTestRule: AndroidComposeTestRule<*, *>
    operator fun invoke(
        composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
        block: KaigiAppRobot.() -> Unit
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
