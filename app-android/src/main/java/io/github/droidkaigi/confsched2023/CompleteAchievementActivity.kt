package io.github.droidkaigi.confsched2023

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import java.security.MessageDigest

class CompleteAchievementActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AnimationScreen(
                deepLink = intent.data.toString(),
                onFinished = {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }
}

fun lastSegmentOfUrl(url: String): String? {
    return url.trim().split("/").lastOrNull()?.takeIf { it.isNotEmpty() }
}

fun idToSha256(id: String?): String {
    if (id == null) return ""
    return MessageDigest.getInstance("SHA-256")
        .digest(id.toByteArray())
        .joinToString(separator = "") {
            "%02x".format(it)
        }
}

@Composable
fun AnimationScreen(
    deepLink: String,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var rawId by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    LaunchedEffect(Unit) {
        val achievementHash = lastSegmentOfUrl(deepLink)
        rawId = when (achievementHash) {
            idToSha256("Arctic Fox") -> io.github.droidkaigi.confsched2023.feature.stamps.R.raw.stamp_a_lottie
            idToSha256("Bumblebee") -> io.github.droidkaigi.confsched2023.feature.stamps.R.raw.stamp_b_lottie
            idToSha256("Chipmunk") -> io.github.droidkaigi.confsched2023.feature.stamps.R.raw.stamp_c_lottie
            idToSha256("Dolphin") -> io.github.droidkaigi.confsched2023.feature.stamps.R.raw.stamp_d_lottie
            idToSha256("Electric Eel") -> io.github.droidkaigi.confsched2023.feature.stamps.R.raw.stamp_e_lottie

            else -> null
        }
    }
    KaigiTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            if (rawId != null) {
                val lottieComposition by rememberLottieComposition(RawRes(rawId!!))
                val progress by animateLottieCompositionAsState(
                    composition = lottieComposition,
                    isPlaying = true,
                    restartOnPlay = true,
                )
                if (progress == 1f) {
                    rawId = null
                    onFinished()
                }
                LottieAnimation(
                    composition = lottieComposition,
                    progress = { progress },
                )
            }
        }
    }
}
