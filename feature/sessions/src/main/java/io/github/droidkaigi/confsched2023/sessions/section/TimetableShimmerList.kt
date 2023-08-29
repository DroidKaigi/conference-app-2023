package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.sessions.component.TimetableShimmerListItem

const val TimetableShimmerListTestTag = "TimetableShimmerList"

@Composable
fun TimetableShimmerList(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.testTag(TimetableShimmerListTestTag),
        contentPadding = contentPadding,
    ) {
        items(10) {
            Row(modifier = Modifier.padding(start = 16.dp, top = 10.dp, end = 16.dp)) {
                Spacer(modifier = Modifier.size(58.dp))
                TimetableShimmerListItem()
            }
        }
    }
}
