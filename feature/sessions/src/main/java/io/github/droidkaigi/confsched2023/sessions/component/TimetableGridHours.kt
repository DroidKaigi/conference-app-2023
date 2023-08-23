package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HoursItem(
    hour: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = hour,
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimetableGridHours(
    modifier: Modifier = Modifier,
    content: @Composable (String) -> Unit,
) {
    val itemProvider = itemProvider({ hoursList.size }) { index ->
        content(hoursList[index])
    }

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

private val hoursList = listOf(
    "10:00",
    "11:00",
    "12:00",
    "13:00",
    "14:00",
    "15:00",
    "16:00",
    "17:00",
    "18:00",
    "19:00",
)