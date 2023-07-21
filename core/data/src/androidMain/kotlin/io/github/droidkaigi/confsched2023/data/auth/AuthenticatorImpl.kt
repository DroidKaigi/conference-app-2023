package io.github.droidkaigi.confsched2023.data.auth

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import javax.inject.Inject

public class AuthenticatorImpl @Inject constructor() : Authenticator {
    override suspend fun currentUser(): User? {
        Logger.d("currentUser")
        val firebaseUser = Firebase.auth.currentUser ?: return run {
            Logger.d("currentUser: null")
            null
        }
        val idToken = firebaseUser.getIdToken(false) ?: return run {
            Logger.d("idToken: null")
            null
        }

        return User(idToken)
    }

    override suspend fun signInAnonymously(): User? {
        Logger.d("signInAnonymously")
        val result = Firebase.auth.signInAnonymously()
        Logger.d("signin:${result.user}")

        val firebaseUser = result.user ?: return null
        val idToken = firebaseUser.getIdToken(false) ?: return null

        return User(idToken)
    }
}
