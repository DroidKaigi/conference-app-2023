package io.github.droidkaigi.confsched2023.sessions.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.feature.sessions.R

@Composable
fun TimetableHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(168.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(Modifier.padding(start = 16.dp)) {
            Text(text = "DroidKaigi\n2023", style = MaterialTheme.typography.displaySmall)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "at Bellesalle Shibuya Garden",
                style = MaterialTheme.typography.labelMedium,
            )
        }
        Image(
            modifier = Modifier.size(width = 212.dp, height = 168.dp),
            painter = painterResource(id = R.drawable.img_droid_kun_in_bath),
            contentDescription = null,
        )
    }
}
