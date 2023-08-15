package io.github.droidkaigi.confsched2023

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.testing.HiltTestActivity
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import io.github.droidkaigi.confsched2023.testing.robot.AboutScreenRobot
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
class AboutScreenTest {

    @get:Rule
    @BindValue val robotTestRule: RobotTestRule = RobotTestRule<HiltTestActivity>(this)

    @Inject
    lateinit var aboutScreenRobot: AboutScreenRobot

    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchShot() {
        aboutScreenRobot {
            setupAboutScreenContent()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkScrollShot() {
        aboutScreenRobot {
            setupAboutScreenContent()
            scrollAboutScreen()
            checkStaffAboutContentColumnCapture()
            checkContributorsAboutContentColumnCapture()
            checkSponsorsAboutContentColumnCapture()
            scrollAboutScreen()
            checkCodeOfConductAboutContentColumnCapture()
            checkLicenseAboutContentColumnCapture()
            checkPrivacyPolicyAboutContentColumnCapture()
        }
    }
}
