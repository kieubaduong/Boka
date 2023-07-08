package com.example.boka.data.network.history.result

import com.example.boka.data.network.book.result.BookJson
import com.google.gson.annotations.SerializedName

data class BookRatingResult(
    val id : Int,
    val rating : Int,
    @SerializedName("user_id")
    val userId : Int,
    @SerializedName("book_id")
    val bookId : Int,
    val book : BookJson,
){
    fun toBook() = book.toBook()
}
