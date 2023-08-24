package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.droidkaigi.confsched2023.feature.sessions.R

@Composable
fun BookmarkIcon(
    isBookmarked: Boolean,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isPlaying by remember { mutableStateOf(false) }
    val lottieComposition by rememberLottieComposition(RawRes(R.raw.add_to_bookmark_lottie))
    val progress by animateLottieCompositionAsState(
        composition = lottieComposition,
        isPlaying = isPlaying,
    )

    Box(
        modifier = modifier
            .clickable {
                isPlaying = isBookmarked.not()
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        if (isBookmarked) {
            LottieAnimation(
                composition = lottieComposition,
                progress = { progress },
                modifier = Modifier
                    .semantics {
                        onClick(label = contentDescription, action = null)
                    }
            )
        } else {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = contentDescription,
            )
        }
    }
}
