package com.example.boka.data.data_source.network.api

import com.example.boka.data.data_source.network.book.result.BookJson
import retrofit2.Response
import retrofit2.http.GET

interface BookApi {
    @GET("books/top_rated")
    suspend fun getTopRatedBooks() : Response<List<BookJson>>
}