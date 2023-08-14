package io.github.droidkaigi.confsched2023.about.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2023.about.AboutStrings

@Composable
fun AboutDroidKaigiDetailSummaryCardRow(
    leadingIcon: ImageVector,
    label: String,
    content: String,
    modifier: Modifier = Modifier,
    leadingIconContentDescription: String? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            imageVector = leadingIcon,
            contentDescription = leadingIconContentDescription,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge,
        )
        Spacer(modifier = Modifier.width(12.dp))
        ClickableLinkText(
            style = MaterialTheme.typography.bodyMedium,
            content = content,
            onLinkClick = {}, // TODO Go to FloorMapScreen
        )
    }
}

// TODO The following components can be commonized and made available on the session screen.
@Composable
private fun findResults(
    content: String,
    regex: Regex,
): Sequence<MatchResult> {
    return remember(content) {
        regex.findAll(content)
    }
}

@Composable
private fun getAnnotatedString(
    content: String,
    findUrlResults: Sequence<MatchResult>,
): AnnotatedString {
    return buildAnnotatedString {
        pushStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.inverseSurface,
            ),
        )
        append(content)
        pop()

        var lastIndex = 0
        findUrlResults.forEach { matchResult ->
            val startIndex = content.indexOf(
                string = matchResult.value,
                startIndex = lastIndex,
            )
            val endIndex = startIndex + matchResult.value.length
            addStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold,
                ),
                start = startIndex,
                end = endIndex,
            )
            addStringAnnotation(
                tag = matchResult.value,
                annotation = matchResult.value,
                start = startIndex,
                end = endIndex,
            )

            lastIndex = endIndex
        }
    }
}

@Composable
fun ClickableLinkText(
    style: TextStyle,
    content: String,
    onLinkClick: (url: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val findResults = findResults(
        content = content,
        regex = AboutStrings.PlaceLink.asString().toRegex(),
    )
    val annotatedString = getAnnotatedString(
        content = content,
        findUrlResults = findResults,
    )

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = style,
        onClick = { offset ->
            findResults.forEach { matchResult ->
                annotatedString.getStringAnnotations(
                    tag = matchResult.value,
                    start = offset,
                    end = offset,
                ).firstOrNull()?.let {
                    onLinkClick(matchResult.value)
                }
            }
        },
    )
}
