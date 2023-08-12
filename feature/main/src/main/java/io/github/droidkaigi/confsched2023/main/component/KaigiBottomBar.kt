package io.github.droidkaigi.confsched2023.main.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import io.github.droidkaigi.confsched2023.main.MainScreenTab

@Composable
fun KaigiBottomBar(
    mainScreenTabs: List<MainScreenTab>,
    onTabSelected: (MainScreenTab) -> Unit,
    currentTab: MainScreenTab,
    isEnableStamps: Boolean,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        mainScreenTabs.forEach { tab ->
            if (tab == MainScreenTab.Stamps && isEnableStamps.not()) return@forEach
            val selected = currentTab == tab
            NavigationBarItem(
                modifier = Modifier.testTag(tab.testTag),
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.icon,
                        contentDescription = tab.contentDescription,
                    )
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
