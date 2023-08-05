package io.github.droidkaigi.confsched2023.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode

@Composable
inline fun previewOverride(
    previewPainter: @Composable () -> Painter,
    crossinline painter: @Composable () -> Painter,
): Painter {
    if (LocalInspectionMode.current) return previewPainter()
    if (isTest()) return previewPainter()
    return painter()
}
