package com.example.boka.data.data_source.network.api

import com.example.boka.data.data_source.network.auth.body.SignInBody
import com.example.boka.data.data_source.network.auth.result.SignInResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/sign_in")
    suspend fun signIn(@Body request: SignInBody) : Response<SignInResult>
}