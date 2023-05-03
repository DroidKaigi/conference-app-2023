package io.github.droidkaigi.confsched2023.testing

import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class HiltInjectRule(private val rule: HiltAndroidRule) : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        rule.inject()
    }
}
