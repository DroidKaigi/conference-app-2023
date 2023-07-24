package io.github.droidkaigi.confsched2023.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.seiko.imageloader.rememberImagePainter

@Composable
fun rememberAsyncImagePainter(url: String): Painter {
    return rememberImagePainter(url = url)
}
