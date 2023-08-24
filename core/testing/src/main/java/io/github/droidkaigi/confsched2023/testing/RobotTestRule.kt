package io.github.droidkaigi.confsched2023.testing

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.RoborazziOptions.CompareOptions
import com.github.takahirom.roborazzi.RoborazziOptions.PixelBitConfig
import com.github.takahirom.roborazzi.RoborazziOptions.RecordOptions
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.RoborazziRule.Options
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement

inline fun <reified A : ComponentActivity> RobotTestRule(
    testInstance: Any,
    bundle: Bundle? = null,
): RobotTestRule {
    val composeTestRule = AndroidComposeTestRule<ActivityScenarioRule<A>, A>(
        activityRule = ActivityScenarioRule(
            Intent(
                ApplicationProvider.getApplicationContext(),
                A::class.java,
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (bundle != null) {
                    putExtras(bundle)
                }
            },
        ),
        activityProvider = { rule ->
            var activity: A? = null
            rule.scenario.onActivity { activity = it }
            if (activity == null) {
                error("Activity was not set in the ActivityScenarioRule!")
            }
            return@AndroidComposeTestRule activity!!
        },
    )
    return RobotTestRule(
        testInstance,
        composeTestRule as AndroidComposeTestRule<ActivityScenarioRule<*>, *>,
    )
}

class RobotTestRule(
    private val testInstance: Any,
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<*>, *>,
) : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return RuleChain
            .outerRule(HiltAndroidAutoInjectRule(testInstance))
            .around(CoroutinesTestRule())
            .around(object : TestWatcher() {
                override fun starting(description: Description) {
                    // To see logs in the console
                    Logger.setLogWriters(CommonWriter())
                }
            })
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
