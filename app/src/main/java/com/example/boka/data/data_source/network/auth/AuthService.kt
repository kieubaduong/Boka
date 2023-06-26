package com.example.boka.data.data_source.network.auth

import com.example.boka.data.data_source.network.auth.body.SignInBody
import com.example.boka.data.data_source.network.auth.result.SignInResult
import com.example.boka.data.model.NetworkResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("auth")
    suspend fun getAuth() : NetworkResult<SignInResult>
    @POST("auth/sign_in")
    suspend fun signIn(@Body request: SignInBody) : NetworkResult<SignInResult>
}