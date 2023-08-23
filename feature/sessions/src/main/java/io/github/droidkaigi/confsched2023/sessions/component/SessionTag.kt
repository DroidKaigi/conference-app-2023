package io.github.droidkaigi.confsched2023.sessions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.confsched2023.designsystem.preview.MultiThemePreviews
import io.github.droidkaigi.confsched2023.designsystem.theme.KaigiTheme
import io.github.droidkaigi.confsched2023.designsystem.theme.hallColors

@Composable
fun SessionTag(
    label: String,
    modifier: Modifier = Modifier,
    labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    backgroundColor: Color = Color.Transparent,
    borderColor: Color? = null,
) {
    Box(
        modifier = modifier
            .height(24.dp)
            .then(
                if (borderColor != null) {
                    Modifier.border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(50.dp),
                    )
                } else {
                    Modifier
                },
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(50.dp),
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = labelColor,
        )
    }
}

@MultiThemePreviews
@Composable
fun SessionTagPreview() {
    val hallColor = hallColors()
    KaigiTheme {
        Surface {
            SessionTag(
                label = "Chipmunk",
                labelColor = hallColor.hallText,
                backgroundColor = hallColor.hallC,
            )
        }
    }
}
