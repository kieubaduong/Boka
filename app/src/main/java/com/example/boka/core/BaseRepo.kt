package com.example.boka.core

import com.example.boka.data.model.NetworkResult

open class BaseRepo {
    fun <T : Any, R : Any> handleNetworkResult(
        result: NetworkResult<T>,
        transform: (T) -> R,
    ): NetworkResult<R> {
        result.data
        return try {
            result.data?.let {
                NetworkResult(data = transform(it))
            } ?: NetworkResult(error = "Repo layer null data: ${result.error}")
        } catch (e: Exception) {
            NetworkResult(error = "Repo layer exception: ${e.message}")
        }
    }

}