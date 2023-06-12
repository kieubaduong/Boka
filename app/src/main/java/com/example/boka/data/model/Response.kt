package com.example.boka.data.model

data class Response<T>(
    val data: T? = null,
    val message: String?,
    val token : String?,
    val error : String?,
)
