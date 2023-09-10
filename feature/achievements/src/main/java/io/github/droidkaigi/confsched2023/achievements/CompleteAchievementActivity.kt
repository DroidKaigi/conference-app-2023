package io.github.droidkaigi.confsched2023.achievements

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.github.droidkaigi.confsched2023.achievements.animation.AchievementAnimationScreen
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme

@AndroidEntryPoint
class CompleteAchievementActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KaigiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    AchievementAnimationScreen(
                        deepLink = intent.data.toString(),
                        onFinished = {
                            finish()
                        },
                    )
                }
            }
        }
    }
}
