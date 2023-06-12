package com.example.boka.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val userId : Int? = null,
    val location : String? = null,
    val age : Int? = null,
    val email : String? = null,
)
