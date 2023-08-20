package io.github.droidkaigi.confsched2023.sessions

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.testing.HiltTestActivity
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import io.github.droidkaigi.confsched2023.testing.robot.SearchScreenRobot
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
class SearchScreenTest {
    @get:Rule
    @BindValue val robotTestRule: RobotTestRule = RobotTestRule<HiltTestActivity>(this)

    @Inject
    lateinit var searchScreenRobot: SearchScreenRobot

    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchShot() {
        searchScreenRobot {
            setupSearchScreenContent()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkFilterDayChipShot() {
        searchScreenRobot {
            setupSearchScreenContent()
            clickFilterDayChip()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkFilterCategoryChipShot() {
        searchScreenRobot {
            setupSearchScreenContent()
            clickFilterCategoryChip()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkFilterSessionTypeChipShot() {
        searchScreenRobot {
            setupSearchScreenContent()
            clickFilterSessionTypeChip()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkFilterLanguageChipShot() {
        searchScreenRobot {
            setupSearchScreenContent()
            scrollSearchFilterHorizontally()
            clickFilterLanguageChip()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkEmptyBodyShot() {
        searchScreenRobot {
            setupSearchScreenContent()
            inputDummyTextSearchTextFieldAppBar()
            checkExistEmptyBody()
            checkScreenCapture()
        }
    }
}
