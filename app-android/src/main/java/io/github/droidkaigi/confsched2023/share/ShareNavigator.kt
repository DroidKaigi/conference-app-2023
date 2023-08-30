package io.github.droidkaigi.confsched2023.share

import android.content.ActivityNotFoundException
import android.content.Context
import androidx.core.app.ShareCompat
import co.touchlab.kermit.Logger

class ShareNavigator(private val context: Context) {
    fun share(text: String) {
        try {
            ShareCompat.IntentBuilder(context)
                .setText(text)
                .setType("text/plain")
                .startChooser()
        } catch (e: ActivityNotFoundException) {
            Logger.e("ActivityNotFoundException Fail startActivity: $e")
        }
    }
}
