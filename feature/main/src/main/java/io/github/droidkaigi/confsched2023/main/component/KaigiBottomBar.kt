package io.github.droidkaigi.confsched2023.main.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import io.github.droidkaigi.confsched2023.main.IconRepresentation.Drawable
import io.github.droidkaigi.confsched2023.main.IconRepresentation.Vector
import io.github.droidkaigi.confsched2023.main.MainScreenTab
import kotlinx.collections.immutable.PersistentList

@Composable
fun KaigiBottomBar(
    mainScreenTabs: PersistentList<MainScreenTab>,
    onTabSelected: (MainScreenTab) -> Unit,
    currentTab: MainScreenTab,
    isEnableAchievements: Boolean,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        mainScreenTabs.forEach { tab ->
            if (tab == MainScreenTab.Achievements && isEnableAchievements.not()) return@forEach
            val selected = currentTab == tab
            val targetIcon = if (selected) tab.selectedIcon else tab.icon
            NavigationBarItem(
                modifier = Modifier.testTag(tab.testTag),
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    when (targetIcon) {
                        is Drawable -> Icon(
                            painterResource(id = targetIcon.drawableId),
                            contentDescription = tab.contentDescription,
                        )

                        is Vector -> Icon(
                            imageVector = targetIcon.imageVector,
                            contentDescription = tab.contentDescription,
                        )
                    }
                },
                label = {
                    Text(
                        text = tab.label,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                },
            )
        }
    }
}
