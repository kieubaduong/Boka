package com.example.boka.data.network.book.result

import com.google.gson.annotations.SerializedName

data class RatingJson(
    val id : Int,
    val rating : Int,
    @SerializedName("user_id")
    val userId : Int,
    @SerializedName("book_id")
    val bookId : Int,
    val book : BookJson,
    @SerializedName("created_at")
    val createdAt : String,
    @SerializedName("updated_at")
    val updatedAt : String,
)
