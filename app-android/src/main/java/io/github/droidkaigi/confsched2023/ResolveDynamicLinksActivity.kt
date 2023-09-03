package io.github.droidkaigi.confsched2023

import android.content.Intent
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
    }

    private fun handleDeepLink(deepLink: Uri?) {
        if (deepLink != null) {
            if (deepLink.toString().startsWith("https://droidkaigi.jp/apps/achievements/")) {
                val intent = Intent(this, CompleteAchievementActivity::class.java)
                intent.data = deepLink
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
