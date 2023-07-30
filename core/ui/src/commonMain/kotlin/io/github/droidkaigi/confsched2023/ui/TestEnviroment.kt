package io.github.droidkaigi.confsched2023.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode

@Composable
fun Painter.overridePreviewWith(previewPainter: @Composable () -> Painter): Painter {
    if (LocalInspectionMode.current) return previewPainter()
    if (isTest()) return previewPainter()
    return this
}

expect fun isTest(): Boolean
