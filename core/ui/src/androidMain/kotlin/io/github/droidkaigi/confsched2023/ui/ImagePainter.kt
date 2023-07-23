package io.github.droidkaigi.confsched2023.ui

import androidx.compose.runtime.Composable
import com.seiko.imageloader.rememberImagePainter

@Composable
fun rememberAsyncImagePainter(url: String) = rememberImagePainter(url = url)
