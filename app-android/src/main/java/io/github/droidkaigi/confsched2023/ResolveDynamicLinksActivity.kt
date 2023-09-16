package io.github.droidkaigi.confsched2023

import android.content.Intent
import android.content.Intent.CATEGORY_BROWSABLE
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_REQUIRE_DEFAULT
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import io.github.droidkaigi.confsched2023.model.Lang.ENGLISH
import io.github.droidkaigi.confsched2023.model.Lang.JAPANESE
import io.github.droidkaigi.confsched2023.model.Lang.MIXED
import io.github.droidkaigi.confsched2023.model.defaultLang

class ResolveDynamicLinksActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                if (pendingDynamicLinkData != null) {
                    val deepLink = pendingDynamicLinkData.link
                    handleDeepLink(deepLink)
                } else {
                    val message = when (defaultLang()) {
                        MIXED -> "Please connect to the network/ネットワークに接続してください"
                        JAPANESE -> "ネットワークに接続してください"
                        ENGLISH -> "Please connect to the network"
                    }
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    finishAndRemoveTask()
                }
            }
            .addOnFailureListener {
                finishAndRemoveTask()
            }
    }

    private fun handleDeepLink(deepLink: Uri?) {
        if (deepLink == null || deepLink.host != "2023.droidkaigi.jp") {
            // For a security reason.
            // Activities that can handle ACTION_VIEW must check the authority of Uri.
            finishAndRemoveTask()
            return
        }
        val mainIntent = Intent(this, MainActivity::class.java).apply {
            flags = FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(mainIntent)
        try {
            val intent = Intent(Intent.ACTION_VIEW, deepLink).apply {
                // Disable showing up chooser dialog and don't allow open this link by other apps even if users choose the default app manually.
                `package` = packageName
                addCategory(CATEGORY_BROWSABLE)
                // new task is important for gateway activities like this
                flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_REQUIRE_DEFAULT
            }
            startActivity(intent)
        } finally {
            finishAndRemoveTask()
        }
    }
}
