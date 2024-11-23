package com.example.data.repository

import com.example.domain.oauth.AuthLocalDataSource
import com.example.domain.oauth.OAuthManager
import com.example.domain.oauth.entity.OAuthCredentials
import com.example.domain.repository.OAuthRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class OAuthRepositoryImpl @Inject constructor(
        private val authManager: OAuthManager,
        private val authDataSource: AuthLocalDataSource
): OAuthRepository {

    override suspend fun login(): Result<Boolean> = runCatching{
        authManager.initOAuthCredentials()
    }

    override suspend fun login(token: String): Result<Boolean> = runCatching {
        authManager.handleOAuthListeners(OAuthCredentials(token))
    }

    override suspend fun getOAuthCredentials(): Flow<OAuthCredentials> = authDataSource.getOAuthCredentials()
}