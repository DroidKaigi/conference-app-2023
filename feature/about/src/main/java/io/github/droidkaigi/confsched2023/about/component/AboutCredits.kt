package io.github.droidkaigi.confsched2023.about.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

const val AboutCreditsSponsorsItemTestTag = "AboutCreditsSponsorsItem"
const val AboutCreditsContributorsItemTestTag = "AboutCreditsContributorsItem"
const val AboutCreditsStaffItemTestTag = "AboutCreditsStaffItem"

fun LazyListScope.aboutCredits(
    onStaffItemClick: () -> Unit,
    onContributorsItemClick: () -> Unit,
    onSponsorsItemClick: () -> Unit,
) {
    item {
        Text("Credits")
    }
    item {
        Text(
            modifier = Modifier
                .testTag(AboutCreditsContributorsItemTestTag)
                .clickable { onContributorsItemClick() },
            text = "Go to contributors screen",
        )
    }
    item {
        Text(
            modifier = Modifier
                .testTag(AboutCreditsStaffItemTestTag)
                .clickable { onStaffItemClick() },
            text = "Go to staff screen",
        )
    }
    item {
        Text(
            modifier = Modifier
                .testTag(AboutCreditsSponsorsItemTestTag)
                .clickable { onSponsorsItemClick() },
            text = "Go to sponsors screen",
        )
    }
}
