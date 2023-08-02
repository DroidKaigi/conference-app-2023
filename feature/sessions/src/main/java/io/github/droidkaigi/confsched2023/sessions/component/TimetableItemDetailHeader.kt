package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimetableItemDetailHeader(
    headerTitle: String,
    onClickBackPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.Start,
    ) {
        IconButton(
            onClick = { onClickBackPress() },
            modifier = Modifier.padding(top = 20.dp, bottom = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
        }
        Text(
            text = headerTitle,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
        )
    }
}
