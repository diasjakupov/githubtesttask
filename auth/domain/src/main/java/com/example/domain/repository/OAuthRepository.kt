package com.example.domain.repository

import com.example.domain.oauth.entity.OAuthCredentials
import kotlinx.coroutines.flow.Flow

interface OAuthRepository {

    suspend fun login(): Result<Boolean>
    suspend fun login(token: String): Result<Boolean>

    suspend fun getOAuthCredentials(): Flow<OAuthCredentials>
}