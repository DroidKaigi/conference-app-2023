package io.github.droidkaigi.confsched2023.achievements.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.Achievement

@Composable
fun AchievementImage(
    achievement: Achievement,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = achievement.getDrawableResId()),
        contentDescription = achievement.contentDescription,
        modifier = modifier
            .padding(horizontal = 21.dp),
    )
}
