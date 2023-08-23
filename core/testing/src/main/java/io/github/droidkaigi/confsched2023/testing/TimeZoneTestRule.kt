package io.github.droidkaigi.confsched2023.testing

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.TimeZone

class TimeZoneTestRule : TestWatcher() {
    override fun starting(description: Description) {
        super.starting(description)
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"))
    }
}
