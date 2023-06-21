package com.example.boka.data.model

data class Response<T>(
    val data: T? = null,
    val message: String? = null,
    val token : String? = null,
    val error : String? = null,
)
