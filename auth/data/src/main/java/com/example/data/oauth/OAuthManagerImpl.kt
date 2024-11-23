package com.example.data.oauth

import com.example.domain.oauth.AuthLocalDataSource
import com.example.domain.oauth.OAuthManager
import com.example.domain.oauth.entity.OAuthCredentials
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class OAuthManagerImpl @Inject constructor(
        private val authDataSource: AuthLocalDataSource
) : OAuthManager {

    override suspend fun initOAuthCredentials(): Boolean {
        val authResult = FirebaseAuth.getInstance().pendingAuthResult?.await()
        return handleOAuthListeners(OAuthCredentials(accessToken = (authResult?.credential as? OAuthCredential)?.accessToken.orEmpty()))
    }

    override suspend fun handleOAuthListeners(creds: OAuthCredentials?): Boolean{
        if(creds == null || creds.accessToken.isEmpty()) return false
        authDataSource.putOAuthCredentials(creds)
        return true
    }

    companion object {

        val provider = OAuthProvider.newBuilder("github.com").build()
    }
}