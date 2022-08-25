package com.gsrocks.news.core.presentation

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()

    data class Failure<T>(
        val error: Throwable? = null,
        val message: String? = null
    ) : Resource<T>()

    class Loading<T> : Resource<T>()
}
