package com.example.boka.data.network.api

import com.example.boka.core.BodyResult
import com.example.boka.data.network.auth.body.SignInBody
import com.example.boka.data.network.auth.result.SignInResult
import com.example.boka.data.network.auth.result.UserJson
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @GET("auth")
    suspend fun getAuth() : Response<BodyResult<UserJson>>
    @POST("auth/sign_in")
    suspend fun signIn(@Body request: SignInBody) : Response<SignInResult>
}