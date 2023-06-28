package com.example.boka.core

import com.example.boka.data.model.NetworkResult
import retrofit2.Response

open class BaseService {
    suspend fun <T : Any> callApi(call: suspend () -> Response<T>): NetworkResult<T> {
        val response = call.invoke()
        return if (response.isSuccessful) {
            NetworkResult(data = response.body())
        } else {
            NetworkResult(error = "Service layer: ${response.code()} ${response.message()}")
        }
    }
}