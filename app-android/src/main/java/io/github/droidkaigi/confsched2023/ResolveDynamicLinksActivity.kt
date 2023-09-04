package io.github.droidkaigi.confsched2023

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.CATEGORY_BROWSABLE
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_REQUIRE_DEFAULT
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class ResolveDynamicLinksActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                if (pendingDynamicLinkData != null) {
                    val deepLink = pendingDynamicLinkData.link

                    handleDeepLink(deepLink)
                }
            }
            .addOnFailureListener {
                finishAndRemoveTask()
            }
    }

    private fun handleDeepLink(deepLink: Uri?) {
        if (deepLink == null) return finishAndRemoveTask()
        if (deepLink.host != "2023.droidkaigi.jp") {
            // For a security reason.
            // Activities that can handle ACTION_VIEW must check the authority of Uri.
            finishAndRemoveTask()
            return
        }
        try {
            val intent = Intent(Intent.ACTION_VIEW, deepLink).apply {
                // Disable showing up chooser dialog and don't allow open this link by other apps even if users choose the default app manually.
                `package` = packageName
                addCategory(CATEGORY_BROWSABLE)
                // new task is important for gateway activities like this
                flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_REQUIRE_DEFAULT
            }
            startActivity(intent)
        } catch (ignore: ActivityNotFoundException) {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        } finally {
            finishAndRemoveTask()
        }
    }
}
