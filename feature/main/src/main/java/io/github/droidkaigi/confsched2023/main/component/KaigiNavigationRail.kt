package io.github.droidkaigi.confsched2023.main.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
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
fun KaigiNavigationRail(
    mainScreenTabs: PersistentList<MainScreenTab>,
    onTabSelected: (MainScreenTab) -> Unit,
    currentTab: MainScreenTab,
    isEnableStamps: Boolean,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
    ) {
        mainScreenTabs.forEach { tab ->
            if (tab == MainScreenTab.Badges && isEnableStamps.not()) return@forEach
            val selected = currentTab == tab
            NavigationRailItem(
                modifier = Modifier.testTag(tab.testTag),
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    if (selected) {
                        when (val icon = tab.selectedIcon) {
                            is Drawable -> Icon(
                                painterResource(id = icon.drawableId),
                                contentDescription = tab.contentDescription,
                            )

                            is Vector -> Icon(
                                imageVector = icon.imageVector,
                                contentDescription = tab.contentDescription,
                            )
                        }
                    } else {
                        when (val icon = tab.icon) {
                            is Drawable -> Icon(
                                painterResource(id = icon.drawableId),
                                contentDescription = tab.contentDescription,
                            )

                            is Vector -> Icon(
                                imageVector = icon.imageVector,
                                contentDescription = tab.contentDescription,
                            )
                        }
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
