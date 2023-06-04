package io.github.droidkaigi.confsched2023.testing

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

inline fun <reified A : ComponentActivity>RobotTestRule(testInstance: Any): RobotTestRule<A> {
    val composeTestRule = createAndroidComposeRule<A>()
    return RobotTestRule(testInstance, composeTestRule)
}
class RobotTestRule<T : ComponentActivity>(
    private val testInstance: Any,
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<T>, T>
) : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return RuleChain
            .outerRule(HiltAndroidAutoInjectRule(testInstance))
            .around(composeTestRule)
            .apply(base, description)
    }
}
