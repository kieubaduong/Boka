package com.example.boka.data.network.api

import com.example.boka.core.BodyResult
import com.example.boka.data.model.Genre
import retrofit2.Response
import retrofit2.http.GET

interface MasterApi {
    @GET("genres")
    suspend fun getGenres() : Response<BodyResult<List<Genre>>>
}