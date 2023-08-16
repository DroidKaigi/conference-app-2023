package io.github.droidkaigi.confsched2023.about.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.about.AboutStrings

const val AboutOthersCodeOfConductItemTestTag = "AboutOthersCodeOfConductItem"
const val AboutOthersLicenseItemTestTag = "AboutOthersLicenseItem"
const val AboutOthersPrivacyPolicyItemTestTag = "AboutOthersPrivacyPolicyItem"

fun LazyListScope.aboutOthers(
    onCodeOfConductItemClick: () -> Unit,
    onLicenseItemClick: () -> Unit,
    onPrivacyPolicyItemClick: () -> Unit,
) {
    item {
        Text(
            text = AboutStrings.OthersTitle.asString(),
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
            leadingIcon = Icons.Outlined.Gavel,
            label = AboutStrings.CodeOfConduct.asString(),
            testTag = AboutOthersCodeOfConductItemTestTag,
            onClickAction = onCodeOfConductItemClick,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                ),
        )
    }
    item {
        AboutContentColumn(
            leadingIcon = Icons.Outlined.FileCopy,
            label = AboutStrings.License.asString(),
            testTag = AboutOthersLicenseItemTestTag,
            onClickAction = onLicenseItemClick,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                ),
        )
    }
    item {
        AboutContentColumn(
            leadingIcon = Icons.Outlined.PrivacyTip,
            label = AboutStrings.PrivacyPolicy.asString(),
            testTag = AboutOthersPrivacyPolicyItemTestTag,
            onClickAction = onPrivacyPolicyItemClick,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                ),
        )
    }
}
