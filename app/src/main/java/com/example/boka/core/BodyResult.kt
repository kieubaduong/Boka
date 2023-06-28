package com.example.boka.core


open class BodyResult<T>(
    val data : T,
    val message : String = "",
    val token : String?,
)