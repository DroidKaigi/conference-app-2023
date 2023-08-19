package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.model.TimetableRoom
import io.github.droidkaigi.confsched2023.sessions.section.TimetableState
import kotlinx.coroutines.CoroutineScope

@Composable
fun RoomItem(
    room: TimetableRoom,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = room.name.currentLangTitle,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimetableGridRooms(
    rooms: List<TimetableRoom>,
    timetableState: TimetableState,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
    content: @Composable (TimetableRoom) -> Unit,
) {
    val itemProvider = itemProvider({ rooms.size }) { index ->
        content(rooms[index])
    }
    val density = timetableState.density
}

@OptIn(ExperimentalFoundationApi::class)
private fun itemProvider(
    itemCount: () -> Int,
    itemContent: @Composable (Int) -> Unit
): LazyLayoutItemProvider {
    return object : LazyLayoutItemProvider {
        @Composable
        override fun Item(index: Int, key: Any) {
            itemContent(index)
        }

        override val itemCount: Int get() = itemCount()
    }
}

private object RoomsSizes {
    val headerHeight = 48.dp
}