package com.example.ecommerce_template.core

sealed class ApiResult<out T> {
    data object Loading : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(
        val code: Int? = null,
        val message: String,
        val httpStatus: Int? = null
    ) : ApiResult<Nothing>()

    inline fun <R> map(transform: (T) -> R): ApiResult<R> = when (this) {
        is Loading -> Loading
        is Success -> Success(transform(data))
        is Error -> this
    }
}

inline fun <T> ApiResult<T>.getOrNull(): T? = if (this is ApiResult.Success) data else null

inline fun <T> ApiResult<T>.errorMessageOrNull(): String? =
    if (this is ApiResult.Error) message else null
