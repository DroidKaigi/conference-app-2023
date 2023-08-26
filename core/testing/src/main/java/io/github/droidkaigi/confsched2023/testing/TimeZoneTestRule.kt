package io.github.droidkaigi.confsched2023.testing

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.TimeZone

class TimeZoneTestRule : TestWatcher() {
    private lateinit var evacuatedTimeZone: TimeZone

    override fun starting(description: Description) {
        evacuatedTimeZone = TimeZone.getDefault()
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tokyo"))
    }

    override fun finished(description: Description) {
        TimeZone.setDefault(evacuatedTimeZone)
    }
}
