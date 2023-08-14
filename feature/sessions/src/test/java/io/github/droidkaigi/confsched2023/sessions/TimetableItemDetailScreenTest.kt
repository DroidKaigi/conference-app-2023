package io.github.droidkaigi.confsched2023.sessions

import android.os.Bundle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.testing.HiltTestActivity
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import io.github.droidkaigi.confsched2023.testing.robot.TimetableItemDetailScreenRobot
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@HiltAndroidTest
@Config(
    qualifiers = RobolectricDeviceQualifiers.NexusOne,
)
class TimetableItemDetailScreenTest {

    @get:Rule
    @BindValue val robotTestRule: RobotTestRule = RobotTestRule<HiltTestActivity>(
        testInstance = this,
        bundle = Bundle().apply {
            putString(
                timetableItemDetailScreenRouteItemIdParameterName,
                TimetableItemDetailScreenRobot.defaultSessionId,
            )
        },
    )

    @Inject
    lateinit var timetableItemDetailScreenRobot: TimetableItemDetailScreenRobot

    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchShot() {
        timetableItemDetailScreenRobot {
            setupScreenContent()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkBookmarkToggleShot() {
        timetableItemDetailScreenRobot {
            setupScreenContent()
            clickBookmarkButton()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkScrollShot() {
        timetableItemDetailScreenRobot {
            setupScreenContent()
            scroll()
            checkScreenCapture()
            scroll()
            checkScreenCapture()
            scroll()
            checkScreenCapture()
            scroll()
            checkScreenCapture()
        }
    }
}
