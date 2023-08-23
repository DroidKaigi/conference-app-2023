package io.github.droidkaigi.confsched2023.designsystem.theme

import android.content.Context
import android.content.res.Resources.NotFoundException
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.core.content.res.ResourcesCompat
import co.touchlab.kermit.Logger
import io.github.droidkaigi.confsched2023.core.designsystem.R
import io.github.droidkaigi.confsched2023.designsystem.theme.FontResource.Montserrat
import org.jetbrains.compose.resources.ExperimentalResourceApi

val fontMap: HashMap<String, FontFamily?> = HashMap()

// https://ishroid.medium.com/custom-font-loading-in-kmp-compose-multiplatform-2eb19865f61b
@ExperimentalResourceApi
@Composable
actual fun fontFamilyResource(fontResource: FontResource): FontFamily? {
    val context: Context = LocalContext.current

    var fontFamily: FontFamily? by
        remember(fontResource.resName) { mutableStateOf(fontMap[fontResource.resName]) }
    if (fontFamily == null) {
        LaunchedEffect(fontResource) {
            fontFamily = try {
                FontFamily(
                    ResourcesCompat.getFont(
                        context,
                        when (fontResource) {
                            Montserrat -> R.font.montserrat_medium
                        },
                    )!!,
                )
            } catch (e: Exception) {
                Logger.e(e.message ?: "Please check if the font file specification is correct.")
                throw NotFoundException()
            }
            fontMap[fontResource.resName] = fontFamily
        }
    }
    return fontFamily
}
