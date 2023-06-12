package com.example.boka.data.api

import com.example.boka.data.model.Response
import com.example.boka.data.model.User
import com.example.boka.data.request.SignInRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("auth")
    suspend fun getAuth() : Response<User>

    @POST("auth/sign_in")
    suspend fun signIn(@Body request: SignInRequest) : Response<User>
}