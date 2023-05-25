package com.example.bookreadingapplication.data.api

import com.example.bookreadingapplication.data.model.Response
import com.example.bookreadingapplication.data.model.User
import com.example.bookreadingapplication.data.request.SignInRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("auth")
    suspend fun getAuth() : Response<User>

    @POST("auth/sign_in")
    suspend fun signIn(@Body request: SignInRequest) : Response<User>
}