package io.github.droidkaigi.confsched2023.about.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.Diversity1
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.about.AboutStrings

const val AboutCreditsStaffItemTestTag = "AboutCreditsStaffItem"
const val AboutCreditsContributorsItemTestTag = "AboutCreditsContributorsItem"
const val AboutCreditsSponsorsItemTestTag = "AboutCreditsSponsorsItem"

fun LazyListScope.aboutCredits(
    onStaffItemClick: () -> Unit,
    onContributorsItemClick: () -> Unit,
    onSponsorsItemClick: () -> Unit,
) {
    item {
        Text(
            text = AboutStrings.CreditsTitle.asString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 32.dp,
                    end = 16.dp,
                ),
        )
    }
    item {
        AboutContentColumn(
            leadingIcon = Icons.Outlined.SentimentVerySatisfied,
            label = AboutStrings.Staff.asString(),
            testTag = AboutCreditsStaffItemTestTag,
            onClickAction = onStaffItemClick,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                ),
        )
    }
    item {
        AboutContentColumn(
            leadingIcon = Icons.Outlined.Diversity1,
            label = AboutStrings.Contributor.asString(),
            testTag = AboutCreditsContributorsItemTestTag,
            onClickAction = onContributorsItemClick,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                ),
        )
    }
    item {
        AboutContentColumn(
            leadingIcon = Icons.Outlined.Apartment,
            label = AboutStrings.Sponsor.asString(),
            testTag = AboutCreditsSponsorsItemTestTag,
            onClickAction = onSponsorsItemClick,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                ),
        )
    }
}
