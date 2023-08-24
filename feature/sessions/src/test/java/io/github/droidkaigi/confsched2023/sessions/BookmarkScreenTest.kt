package io.github.droidkaigi.confsched2023.sessions

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.testing.HiltTestActivity
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import io.github.droidkaigi.confsched2023.testing.robot.BookmarkScreenRobot
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
class BookmarkScreenTest {

    @get:Rule
    @BindValue val robotTestRule: RobotTestRule = RobotTestRule<HiltTestActivity>(this)

    @Inject
    lateinit var bookmarkScreenRobot: BookmarkScreenRobot

    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchShot() {
        bookmarkScreenRobot {
            setupBookmarkScreenContent()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkBookmarkLaunchShot() {
        bookmarkScreenRobot {
            toggleFavorite()
            setupBookmarkScreenContent()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkBookmarkFilterChipToggleShot() {
        bookmarkScreenRobot {
            setupBookmarkScreenContent()
            clickBookmarkFilterChipAll()
            checkScreenCapture()
            clickBookmarkFilterChipDay1()
            checkScreenCapture()
            clickBookmarkFilterChipDay2()
            checkScreenCapture()
            clickBookmarkFilterChipDay3()
            checkScreenCapture()
        }
    }
}
