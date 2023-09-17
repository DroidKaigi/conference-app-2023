package io.github.droidkaigi.confsched2023.achievements.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.Achievement
import io.github.droidkaigi.confsched2023.model.AchievementAnimation

const val AchievementImageATestTag = "AchievementImageATestTag"
const val AchievementImageBTestTag = "AchievementImageBTestTag"
const val AchievementImageCTestTag = "AchievementImageCTestTag"
const val AchievementImageDTestTag = "AchievementImageDTestTag"
const val AchievementImageETestTag = "AchievementImageETestTag"

@Composable
fun AchievementImage(
    achievementAnimation: AchievementAnimation,
    modifier: Modifier = Modifier,
    onAchievementClick: (Achievement) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Image(
        painter = painterResource(id = achievementAnimation.getDrawableResId()),
        contentDescription = achievementAnimation.contentDescription,
        modifier = modifier
            .padding(horizontal = 21.dp)
            .testTag(achievementAnimation.testTag)
            .then(
                if (achievementAnimation.hasAchievement) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                    ) {
                        onAchievementClick(achievementAnimation.achievement)
                    }
                } else {
                    Modifier
                },
            ),
    )
}
