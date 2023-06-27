package com.example.boka.data.model

data class NetworkResult<T>(
    val data: T? = null,
    val error : String? = null,
)
