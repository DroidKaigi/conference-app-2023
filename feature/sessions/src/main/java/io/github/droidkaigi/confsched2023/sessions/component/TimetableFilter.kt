package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

const val TimetableFilterTestTag = "TimetableFilter"

@Composable
fun TimetableFilter(
    enabled: Boolean,
    isChecked: Boolean,
    onFilterClick: () -> Unit,
) {
    Text(
        text = "Filter " + if (isChecked) "ON" else "OFF",
        modifier = Modifier
            .testTag(TimetableFilterTestTag)
            .clickable {
                onFilterClick()
            }
    )
}
