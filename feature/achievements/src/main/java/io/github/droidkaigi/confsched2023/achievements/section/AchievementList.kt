package io.github.droidkaigi.confsched2023.achievements.section

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.droidkaigi.confsched2023.achievements.component.AchievementImage
import io.github.droidkaigi.confsched2023.achievements.component.AchievementsDetail
import io.github.droidkaigi.confsched2023.model.Achievement
import kotlinx.collections.immutable.ImmutableList

private const val AchievementsListColumns = 2
private const val SingleItemSpanCount = 2
private const val DoubleItemSpanCount = 2 / 2

data class AchievementListUiState(
    val achievements: ImmutableList<Achievement>,
    val detailDescription: String,
)

@Composable
fun AchievementList(
    uiState: AchievementListUiState,
    @androidx.annotation.RawRes
    achievementLottieRawId: Int?,
    onAchievementsClick: (Achievement) -> Unit,
    contentPadding: PaddingValues,
    onReachAnimationEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val layoutDirection = LocalLayoutDirection.current
    val bgColor: Color by animateColorAsState(
        if (achievementLottieRawId != null) {
            Color(0xFF37383D)
        } else {
            Color.Transparent
        },
        animationSpec = tween(
            delayMillis = 2,
            durationMillis = 400,
            easing = EaseInOut,
        ),
        label = "",
    )
    Box(
        modifier = Modifier
            .background(bgColor)
            .fillMaxSize(),
    ) {
        if (achievementLottieRawId != null) {
            val lottieComposition by rememberLottieComposition(RawRes(achievementLottieRawId))
            val progress by animateLottieCompositionAsState(
                composition = lottieComposition,
                isPlaying = true,
                restartOnPlay = true,
            )
            if (progress == 1f) {
                onReachAnimationEnd()
            }
            LottieAnimation(
                composition = lottieComposition,
                progress = { progress },
            )
        }
        LazyVerticalGrid(
            columns = Fixed(AchievementsListColumns),
            modifier = modifier,
            contentPadding = PaddingValues(
                start = 16.dp + contentPadding.calculateStartPadding(layoutDirection),
                end = 16.dp + contentPadding.calculateEndPadding(layoutDirection),
                top = 20.dp + contentPadding.calculateTopPadding(),
                bottom = 20.dp + contentPadding.calculateBottomPadding(),
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item(
                key = "achievements_header",
                span = { GridItemSpan(SingleItemSpanCount) },
            ) {
                AchievementsDetail(uiState.detailDescription)
            }
            items(
                items = uiState.achievements,
                key = { achievement -> achievement.hasDrawableResId },
                span = { achievement ->
                    GridItemSpan(
                        if (achievement == uiState.achievements.last() && uiState.achievements.size % AchievementsListColumns != 0) {
                            SingleItemSpanCount
                        } else {
                            DoubleItemSpanCount
                        },
                    )
                },
            ) { achievement ->
                val onAchievementClick = if (achievementLottieRawId != null) {
                    {} // Prevents clicks during animation playback.
                } else {
                    onAchievementsClick
                }
                AchievementImage(
                    achievement = achievement,
                    onAchievementClick = onAchievementClick,
                )
            }
        }
    }
}
