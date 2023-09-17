package io.github.droidkaigi.confsched2023.achievements

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.airbnb.lottie.LottieTask
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.droidkaigi.confsched2023.model.Achievement.ArcticFox
import io.github.droidkaigi.confsched2023.model.Achievement.Bumblebee
import io.github.droidkaigi.confsched2023.model.Achievement.Chipmunk
import io.github.droidkaigi.confsched2023.model.Achievement.Dolphin
import io.github.droidkaigi.confsched2023.model.Achievement.ElectricEel
import io.github.droidkaigi.confsched2023.testing.HiltTestActivity
import io.github.droidkaigi.confsched2023.testing.RobotTestRule
import io.github.droidkaigi.confsched2023.testing.category.ScreenshotTests
import io.github.droidkaigi.confsched2023.testing.robot.AchievementsScreenRobot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.util.concurrent.Executor
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@HiltAndroidTest
@Config(
    qualifiers = RobolectricDeviceQualifiers.NexusOne,
)
class AchievementsScreenTest {

    @get:Rule
    @BindValue val robotTestRule: RobotTestRule = RobotTestRule<HiltTestActivity>(this)

    @Inject
    lateinit var achievementsScreenRobot: AchievementsScreenRobot

    @Before
    fun setup() {
        LottieTask.EXECUTOR = Executor(Runnable::run)
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkLaunchShot() {
        achievementsScreenRobot {
            setupScreenContent()
            checkScreenCapture()
            scroll()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkHighlightImageA() {
        achievementsScreenRobot {
            setupSavedAchievement(ArcticFox)
            setupScreenContent()
            clickAchievementImageA()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkHighlightImageB() {
        achievementsScreenRobot {
            setupSavedAchievement(Bumblebee)
            setupScreenContent()
            clickAchievementImageB()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkHighlightImageC() {
        achievementsScreenRobot {
            setupSavedAchievement(Chipmunk)
            setupScreenContent()
            clickAchievementImageC()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkHighlightImageD() {
        achievementsScreenRobot {
            setupSavedAchievement(Dolphin)
            setupScreenContent()
            clickAchievementImageD()
            checkScreenCapture()
        }
    }

    @Test
    @Category(ScreenshotTests::class)
    fun checkHighlightImageE() {
        achievementsScreenRobot {
            setupSavedAchievement(ElectricEel)
            setupScreenContent()
            clickAchievementImageE()
            checkScreenCapture()
        }
    }
}
