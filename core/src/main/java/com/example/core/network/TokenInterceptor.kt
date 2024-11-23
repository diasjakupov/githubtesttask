package com.example.core.network

import android.content.Context
import android.content.Intent
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class UnauthorizedInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val response = chain.proceed(originalRequest)

        if (response.code == 401) {
            notifyLoginRequired(context)
        }

        return response
    }

    private fun notifyLoginRequired(context: Context) {
        val intent = Intent(ACTION_LOGIN_REQUIRED)
        context.sendBroadcast(intent)
    }

    companion object {
        const val ACTION_LOGIN_REQUIRED = "com.example.network.LOGIN_REQUIRED"
    }
}