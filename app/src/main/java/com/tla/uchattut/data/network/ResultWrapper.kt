package com.tla.uchattut.data.network

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val throwable: Throwable, val code: Int? = null, val error: String? = null) : ResultWrapper<Nothing>()
}