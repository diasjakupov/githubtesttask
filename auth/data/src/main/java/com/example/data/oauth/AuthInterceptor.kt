package com.example.data.oauth

import com.example.domain.oauth.AuthLocalDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor @Inject constructor(private val authDataStore: AuthLocalDataSource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { authDataStore.getOAuthCredentials().firstOrNull()?.accessToken.orEmpty() }
        val request: Request = chain.request()
        val newRequest: Request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Accept", "application/vnd.github+json")
                .build()
        return chain.proceed(newRequest)
    }
}
