package io.github.droidkaigi.confsched2023.sessions

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.sessions.component.FilterCategoryChipTestTag
import io.github.droidkaigi.confsched2023.sessions.component.FilterDayChipTestTag
import io.github.droidkaigi.confsched2023.sessions.component.FilterLanguageChipTestTag
import io.github.droidkaigi.confsched2023.sessions.component.FilterSessionTypeChipTestTag
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
            checkFilterChipShot(FilterDayChipTestTag)
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkFilterCategoryChipShot() {
        searchScreenRobot {
            checkFilterChipShot(FilterCategoryChipTestTag)
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkFilterSessionTypeChipShot() {
        searchScreenRobot {
            checkFilterChipShot(FilterSessionTypeChipTestTag) {
                scrollSearchFilterToLeft()
            }
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkFilterLanguageChipShot() {
        searchScreenRobot {
            checkFilterChipShot(FilterLanguageChipTestTag) {
                scrollSearchFilterToLeft()
            }
        }
    }

    private fun SearchScreenRobot.checkFilterChipShot(
        filterChipTestTag: String,
        scrollToLeft: (() -> Unit)? = null,
    ) {
        setupSearchScreenContent()
        scrollToLeft?.invoke()

        // select item
        clickFilterChip(filterChipTestTag)
        clickFirstDropdownMenuItem()
        checkSearchScreenCapture()

        // select other item
        clickFilterChip(filterChipTestTag)
        clickLastDropdownMenuItem()
        checkSearchScreenCapture()

        // remove all items
        clickFilterChip(filterChipTestTag)
        clickFirstDropdownMenuItem()
        clickFilterChip(filterChipTestTag)
        clickLastDropdownMenuItem()
        checkSearchScreenCapture()
    }
}
