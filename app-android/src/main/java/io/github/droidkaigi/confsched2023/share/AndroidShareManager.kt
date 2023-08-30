package io.github.droidkaigi.confsched2023.share

import android.content.ActivityNotFoundException
import android.content.Context
import android.util.Log
import androidx.core.app.ShareCompat

class AndroidShareManager(private val context: Context) : ShareManager {
    override fun share(text: String) {
        try {
            ShareCompat.IntentBuilder(context)
                .setText(text)
                .setType("text/plain")
                .startChooser()
        } catch (e: ActivityNotFoundException) {
            Log.e("ActivityNotFoundException", "Fail startActivity", e)
        }
    }
}
