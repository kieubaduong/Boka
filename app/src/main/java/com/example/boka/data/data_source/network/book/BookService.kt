package com.example.boka.data.data_source.network.book

import com.example.boka.data.model.Book
import com.example.boka.data.model.Response
import retrofit2.http.GET

interface BookService {
    @GET("book_ratings")
    suspend fun getTopRatedBooks() : Response<List<Book>>
}