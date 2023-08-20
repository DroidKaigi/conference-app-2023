package io.github.droidkaigi.confsched2023

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import io.github.droidkaigi.confsched2023.testing.robot.KaigiAppRobot
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
@Category(
    ScreenshotTests::class,
)
class KaigiAppTest {

    @get:Rule
    @BindValue val robotTestRule: RobotTestRule = RobotTestRule<MainActivity>(this)

    @Inject lateinit var kaigiAppRobot: KaigiAppRobot

    @Test
    fun checkStartupShot() {
        kaigiAppRobot {
            capture()
        }
    }

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.MediumTablet)
    fun checkMediumTabletLaunchShot() {
        kaigiAppRobot {
            capture()
        }
    }

    @Test
    fun checkStartup() {
        kaigiAppRobot {
            timetableScreenRobot {
                checkTimetableItemsDisplayed()
            }
        }
    }

    @Test
    fun checkNavigateToAboutShot() {
        kaigiAppRobot {
            goToAbout()
            capture()
        }
    }

    @Test
    fun checkNavigateToFloorMapShot() {
        kaigiAppRobot {
            goToFloorMap()
            capture()
        }
    }

    @Test
    fun checkNavigateToStampsShot() {
        kaigiAppRobot {
            goToStamps()
            capture()
        }
    }

    @Test
    fun checkNavigateToSponsorsShot() {
        kaigiAppRobot {
            goToAbout()
            aboutScreenRobot {
                clickSponsors()
            }
            capture()
        }
    }

    @Test
    fun checkNavigateToContributorShot() {
        kaigiAppRobot {
            goToContributor()
            capture()
        }
    }

    @Test
    fun checkNavigateToTimetableItemDetailShot() {
        kaigiAppRobot {
            timetableScreenRobot {
                clickFirstSession()
            }
            capture()
        }
    }

    @Test
    fun checkNavigateToSearchShot() {
        kaigiAppRobot {
            timetableScreenRobot {
                clickSearchButton()
            }
            capture()
        }
    }

    @Test
    fun checkNavigateToBookmarkShot() {
        kaigiAppRobot {
            timetableScreenRobot {
                clickBookmarkButton()
            }
            capture()
        }
    }
}
