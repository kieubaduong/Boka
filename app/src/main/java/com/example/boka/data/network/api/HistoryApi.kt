package com.example.boka.data.network.api

import com.example.boka.core.BodyResult
import com.example.boka.data.network.history.result.BookRatingResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoryApi {
    @GET("book_ratings/format")
    suspend fun getUserRating() : Response<BodyResult<Map<String, Int>>>
    @GET("book_ratings")
    suspend fun getRatedBooks(@Query("per_page") perPage : Int) : Response<BodyResult<List<BookRatingResult>>>
}