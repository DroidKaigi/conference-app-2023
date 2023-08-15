package io.github.droidkaigi.confsched2023.about.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.about.AboutStrings
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.feature.about.R

@Composable
fun AboutDroidKaigiDetail(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = AboutStrings.Title.asString(),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 20.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
        )
        Image(
            painter = painterResource(id = R.drawable.img_about_key_visual),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
        )
        Text(
            text = AboutStrings.Description.asString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp,
                ),
        )
        AboutDroidKaigiDetailSummaryCard(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 12.dp,
                    end = 16.dp,
                ),
        )
    }
}

@Preview(locale = "en", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(locale = "ja", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AboutDroidKaigiDetailPreview() {
    KaigiTheme {
        Surface {
            AboutDroidKaigiDetail()
        }
    }
}

@Preview(locale = "en", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(locale = "ja", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AboutDroidKaigiDetailDarkModePreview() {
    KaigiTheme {
        Surface {
            AboutDroidKaigiDetail()
        }
    }
}
