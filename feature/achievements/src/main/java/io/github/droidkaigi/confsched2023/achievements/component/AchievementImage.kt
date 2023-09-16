package io.github.droidkaigi.confsched2023.achievements.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.AchievementAnimation

@Composable
fun AchievementImage(
    achievementAnimation: AchievementAnimation,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = achievementAnimation.getDrawableResId()),
        contentDescription = achievementAnimation.contentDescription,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 21.dp),
    )
}
