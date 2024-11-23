package com.example.core.network

import android.content.Context
import com.example.core.R
import com.example.core.network.utils.InternetConnectivityException
import com.example.core.network.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface NetworkInterceptor {

    suspend fun <T> executeWithNetworkCheck(
            block: suspend () -> T
    ): T
}


class DefaultNetworkInterceptor @Inject constructor(
        @ApplicationContext private val context: Context
) : NetworkInterceptor {

    override suspend fun <T> executeWithNetworkCheck(
            block: suspend () -> T
    ): T {
        return if (isInternetAvailable()) {
            block()
        } else {
            throw InternetConnectivityException(context.getString(R.string.internet_no_connection))
        }
    }

    private suspend fun isInternetAvailable(): Boolean {
        return NetworkUtils.isInternetAvailable(context)
    }
}