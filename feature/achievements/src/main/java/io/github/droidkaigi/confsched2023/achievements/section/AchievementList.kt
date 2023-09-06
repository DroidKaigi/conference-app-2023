package io.github.droidkaigi.confsched2023.achievements.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.achievements.component.AchievementImage
import io.github.droidkaigi.confsched2023.achievements.component.AchievementsDetail
import io.github.droidkaigi.confsched2023.model.AchievementAnimation
import kotlinx.collections.immutable.ImmutableList

private const val AchievementsListColumns = 2
private const val SingleItemSpanCount = 2
private const val DoubleItemSpanCount = 2 / 2

data class AchievementListUiState(
    val achievements: ImmutableList<AchievementAnimation>,
    val detailDescription: String,
    val isResetButtonEnabled: Boolean,
)

@Composable
fun AchievementList(
    uiState: AchievementListUiState,
    contentPadding: PaddingValues,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val layoutDirection = LocalLayoutDirection.current
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
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
            ) { achievementAnimation ->
                AchievementImage(
                    achievementAnimation = achievementAnimation,
                )
            }
            if (uiState.isResetButtonEnabled) {
                item(
                    key = "reset_button",
                    span = { GridItemSpan(SingleItemSpanCount) },
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onReset,
                    ) {
                        Text(text = "Reset")
                    }
                }
            }
        }
    }
}
