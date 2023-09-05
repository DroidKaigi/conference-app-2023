package io.github.droidkaigi.confsched2023.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter

private const val DEFAULT_MAX_IMAGE_SIZE = 1024

@Composable
fun rememberAsyncImagePainter(url: String): Painter {
    val request = remember(url) {
        ImageRequest {
            data(url)
            options {
                maxImageSize = DEFAULT_MAX_IMAGE_SIZE
            }
        }
    }

    return rememberImagePainter(request = request)
}
