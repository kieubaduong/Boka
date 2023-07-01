package com.example.boka.data.network.book.body

import com.google.gson.annotations.SerializedName

data class ReviewBookBody(
    val rating : Int,
    @SerializedName("book_id")
    val bookId : Int
)
