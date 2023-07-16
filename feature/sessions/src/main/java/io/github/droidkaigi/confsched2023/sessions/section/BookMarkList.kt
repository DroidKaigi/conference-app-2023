package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.TimetableItem
import io.github.droidkaigi.confsched2023.model.TimetableItemId
import io.github.droidkaigi.confsched2023.sessions.component.BookMarkItem
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet

@Composable
fun BookMarkList(
    bookmarkedTimetableItemIds: PersistentSet<TimetableItemId>,
    timetableItems: PersistentList<TimetableItem>,
    onClickBooMarkIcon: (TimetableItemId) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier.padding(end = 16.dp)) {
        items(timetableItems) { timetableItem ->
            Row(modifier = Modifier.padding(top = 10.dp)) {
                Column(
                    modifier = Modifier.width(58.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Spacer(modifier = Modifier.size(6.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = timetableItem.startsTimeString,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(text = "|")
                        Text(
                            text = timetableItem.endsTimeString,
                        )
                    }
                }
                BookMarkItem(
                    bookmarkedTimetableItemIds = bookmarkedTimetableItemIds,
                    timetableItem = timetableItem,
                    onClickBooMarkIcon = onClickBooMarkIcon,
                )
            }
        }
    }
}
