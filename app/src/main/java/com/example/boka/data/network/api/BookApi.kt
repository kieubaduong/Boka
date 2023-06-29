package com.example.boka.data.network.api

import com.example.boka.core.BodyResult
import com.example.boka.data.network.book.result.BookJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {
    @GET("books/{bookId}")
    suspend fun getBookDetail(@Path("bookId") bookId : Int) : Response<BodyResult<BookJson>>
    @GET("books/top_rated")
    suspend fun getTopRatedBooks() : Response<BodyResult<List<BookJson>>>
}