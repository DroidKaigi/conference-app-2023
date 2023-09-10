package io.github.droidkaigi.confsched2023.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

private const val CONTRACTION_RATIO = 0.95f

/**
 * ref: https://blog.canopas.com/autosizing-textfield-in-jetpack-compose-7a80f0270853
 */
@Composable
fun AutoSizableText(
    text: String,
    modifier: Modifier = Modifier,
    minFontSize: TextUnit,
    maxLines: Int = Int.MAX_VALUE,
    fontWeight: FontWeight? = null,
    style: TextStyle,
) {
    val density = LocalDensity.current
    var tempFontSize by remember(text, style) { mutableFloatStateOf(style.fontSize.value) }

    // Calculate size before displaying
    BoxWithConstraints(modifier = modifier) {
        val calculateParagraph = @Composable {
            Paragraph(
                text = text,
                style = style.copy(fontSize = tempFontSize.sp),
                constraints = this.constraints,
                density = density,
                fontFamilyResolver = LocalFontFamilyResolver.current,
            )
        }

        var paragraph = calculateParagraph()
        while (
            tempFontSize > minFontSize.value &&
            (paragraph.height / density.density > maxHeight.value || paragraph.lineCount > maxLines)
        ) {
            tempFontSize *= CONTRACTION_RATIO
            paragraph = calculateParagraph()
        }
    }

    Text(
        text = text,
        modifier = modifier,
        maxLines = maxLines,
        fontWeight = fontWeight,
        style = style.copy(fontSize = tempFontSize.sp),
    )
}
