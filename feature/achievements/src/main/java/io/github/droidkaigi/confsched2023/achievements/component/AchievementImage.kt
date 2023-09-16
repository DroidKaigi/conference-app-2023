package io.github.droidkaigi.confsched2023.achievements.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.AchievementAnimation

@Composable
fun AchievementImage(
    achievementAnimation: AchievementAnimation,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Image(
        painter = painterResource(id = achievementAnimation.getDrawableResId()),
        contentDescription = achievementAnimation.contentDescription,
        modifier = modifier.width(screenWidth / 3),
    )
}
