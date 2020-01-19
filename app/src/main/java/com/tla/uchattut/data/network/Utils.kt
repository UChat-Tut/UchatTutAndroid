package com.tla.uchattut.data.network

import android.util.Log


suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall())
    } catch (throwable: Throwable) {
        ResultWrapper.Error(throwable)
    }
}

fun logException(place: Any, throwable: Throwable) {
    Log.e("Error - $place", throwable.message.toString())
}