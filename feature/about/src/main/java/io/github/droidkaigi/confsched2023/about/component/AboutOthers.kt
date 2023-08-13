package io.github.droidkaigi.confsched2023.about.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.ui.platform.testTag

const val AboutOthersCodeOfConductItemTestTag = "AboutOthersCodeOfConductItem"
const val AboutOthersLicenseItemTestTag = "AboutOthersLicenseItem"
const val AboutOthersPrivacyPolicyItemTestTag = "AboutOthersPrivacyPolicyItem"

fun LazyListScope.aboutOthers(
    onCodeOfConductItemClick: () -> Unit,
    onLicenseItemClick: () -> Unit,
    onPrivacyPolicyItemClick: () -> Unit,
) {
    item {
        Text("Others")
    }
    item {
        Text("Others items")
    }
    item {
        Text(
            text = "Go to Code of Conduct screen",
            modifier = androidx.compose.ui.Modifier
                .testTag(AboutOthersCodeOfConductItemTestTag)
                .clickable { onCodeOfConductItemClick() },
        )
    }
    item {
        Text(
            text = "Go to License screen",
            modifier = androidx.compose.ui.Modifier
                .testTag(AboutOthersLicenseItemTestTag)
                .clickable { onLicenseItemClick() },
        )
    }
    item {
        Text(
            text = "Go to Privacy Policy screen",
            modifier = androidx.compose.ui.Modifier
                .testTag(AboutOthersPrivacyPolicyItemTestTag)
                .clickable { onPrivacyPolicyItemClick() },
        )
    }
}
