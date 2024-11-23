package com.example.domain.oauth

import com.example.domain.oauth.entity.OAuthCredentials



interface OAuthManager {

    suspend fun initOAuthCredentials(): Boolean

    suspend fun handleOAuthListeners(creds: OAuthCredentials?): Boolean
}