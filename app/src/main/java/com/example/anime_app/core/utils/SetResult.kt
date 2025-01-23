package com.example.anime_app.core.utils

sealed class SetResult<T>(
    val data:T? = null,
    val errorType:ErrorType? = null
) {

    class OnSuccess<T>(data: T):SetResult<T>(data = data)

    class OnError<T>(errorType: ErrorType?):SetResult<T>(data = null,errorType = errorType)


}

enum class ErrorType{
    NETWORK_ERROR,
    AUTHENTICATION_ERROR,
    UNKNOWN_ERROR
}