package com.example.core.base

import android.util.Log

interface LoggerRepository {

    fun <T> logError(throwable: Throwable): Result<T> {
        Log.e("ERROR: ", throwable.message.orEmpty())
        return Result.failure<T>(throwable)
    }

    fun <T> logSuccess(result: T):Result<T> = Result.success(result)

}