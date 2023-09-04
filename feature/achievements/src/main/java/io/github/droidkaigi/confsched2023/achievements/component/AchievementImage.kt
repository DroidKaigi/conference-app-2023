package io.github.droidkaigi.confsched2023.achievements.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.Achievement

@Composable
<<<<<<< HEAD:feature/stamps/src/main/java/io/github/droidkaigi/confsched2023/stamps/component/StampImage.kt
fun StampImage(
    stamp: Stamp,
=======
fun AchievementImage(
    achievement: Achievement,
    onAchievementClick: (Achievement) -> Unit,
>>>>>>> origin/main:feature/achievements/src/main/java/io/github/droidkaigi/confsched2023/achievements/component/AchievementImage.kt
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = achievement.getDrawableResId()),
        contentDescription = achievement.contentDescription,
        modifier = modifier
<<<<<<< HEAD:feature/stamps/src/main/java/io/github/droidkaigi/confsched2023/stamps/component/StampImage.kt
=======
            .clickable { onAchievementClick(achievement) }
>>>>>>> origin/main:feature/achievements/src/main/java/io/github/droidkaigi/confsched2023/achievements/component/AchievementImage.kt
            .padding(horizontal = 21.dp),
    )
}
