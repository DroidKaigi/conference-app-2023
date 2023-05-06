package io.github.droidkaigi.confsched2023.testing

import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class HiltAndroidAutoInjectRule(
    private val testInstance: Any
) : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        val hiltAndroidRule = HiltAndroidRule(testInstance)
        return RuleChain
            .outerRule(hiltAndroidRule)
            .around(HiltInjectRule(hiltAndroidRule))
            .apply(base, description)
    }
}
