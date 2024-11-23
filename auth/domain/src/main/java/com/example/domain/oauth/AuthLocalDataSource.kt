package com.example.domain.oauth

import com.example.domain.oauth.entity.OAuthCredentials
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {


    suspend fun putOAuthCredentials(creds: OAuthCredentials)
    suspend fun getOAuthCredentials(): Flow<OAuthCredentials>

    companion object{
        const val DATASTORE_NAME = "auth"
        const val OATH_TOKEN = "token"
    }
}