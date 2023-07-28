package io.github.droidkaigi.confsched2023.testing

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.RoborazziOptions.CompareOptions
import com.github.takahirom.roborazzi.RoborazziOptions.PixelBitConfig
import com.github.takahirom.roborazzi.RoborazziOptions.RecordOptions
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.RoborazziRule.Options
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

inline fun <reified A : ComponentActivity> RobotTestRule(testInstance: Any): RobotTestRule {
    val composeTestRule = createAndroidComposeRule<A>()
    return RobotTestRule(testInstance, composeTestRule as AndroidComposeTestRule<ActivityScenarioRule<*>, *>)
}

class RobotTestRule(
    private val testInstance: Any,
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<*>, *>,
) : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return RuleChain
            .outerRule(HiltAndroidAutoInjectRule(testInstance))
            .around(CoroutinesTestRule())
            .around(
                RoborazziRule(
                    Options(
                        roborazziOptions = RoborazziOptions(
                            compareOptions = CompareOptions(
                                // Detect small changes
                                changeThreshold = 0.000001F,
                            ),
                            recordOptions = RecordOptions(
                                // For saving money
                                pixelBitConfig = PixelBitConfig.Rgb565,
                            ),
                        ),
                    ),
                ),
            )
            .around(composeTestRule)
            .apply(base, description)
    }
}
