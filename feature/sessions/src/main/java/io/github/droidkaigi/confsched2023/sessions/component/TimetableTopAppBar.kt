package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.sessions.strings.SessionsStrings.Timetable

const val TimetableUiTypeChangeButtonTestTag = "TimetableUiTypeChangeButton"

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TimetableTopAppBar(state: TimetableScreenScrollState, onTimetableUiChangeClick: () -> Unit) {
    Column {
        // TODO: Implement TopAppBar design
        TopAppBar(
            title = {
                Text(text = "KaigiApp")
            },
            actions = {
                IconButton(
                    modifier = Modifier.testTag(TimetableUiTypeChangeButtonTestTag),
                    onClick = { onTimetableUiChangeClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = Timetable.asString(),
                    )
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    state.onHeaderPositioned(coordinates.size.height.toFloat())
                },
        ) {
            // TODO: Implement header desing(title and image etc..)
            Spacer(modifier = Modifier.height(130.dp))
        }
    }
}