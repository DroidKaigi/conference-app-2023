package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.feature.sessions.R
import io.github.droidkaigi.confsched2023.sessions.SessionsStrings

@Composable
fun EmptySearchResultBody(
    missedQuery: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize().imePadding(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.img_droid_timetable_search_empty),
                contentDescription = null,
                modifier = Modifier.size(width = 138.dp, height = 96.dp),
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = SessionsStrings.SearchResultNotFound(missedQuery).asString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}
