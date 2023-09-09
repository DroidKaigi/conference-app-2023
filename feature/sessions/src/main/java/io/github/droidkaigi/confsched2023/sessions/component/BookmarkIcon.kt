package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.droidkaigi.confsched2023.feature.sessions.R.raw

@Composable
fun BookmarkIcon(
    contentDescription: String,
    onBookmarkClickStatus: Boolean,
    onReachAnimationEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val lottieComposition by rememberLottieComposition(RawRes(raw.add_to_bookmark_lottie))
    var isPlaying by remember { mutableStateOf(false) }

    val state = animateLottieCompositionAsState(
        composition = lottieComposition,
        isPlaying = isPlaying,
        restartOnPlay = true,
    )

    LaunchedEffect(onBookmarkClickStatus) {
        if (onBookmarkClickStatus) {
            isPlaying = true
        }
    }

    if (state.isPlaying && state.isAtEnd) {
        isPlaying = false
        onReachAnimationEnd()
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (state.isPlaying && !state.isAtEnd) {
            LottieAnimation(
                composition = lottieComposition,
                progress = { state.progress },
                modifier = Modifier
                    .semantics {
                        onClick(label = contentDescription, action = null)
                    },
            )
        } else {
            Icon(
                modifier = Modifier
                    .padding(12.dp),
                imageVector = Icons.Outlined.Bookmarks,
                contentDescription = contentDescription,
            )
        }
    }
}
