package com.example.boka.data.network.api

import com.example.boka.core.BodyResult
import retrofit2.Response
import retrofit2.http.GET

interface HistoryApi {
    @GET("book_ratings/format")
    suspend fun getUserRating() : Response<BodyResult<Map<String, Int>>>
}