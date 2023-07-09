package com.example.boka.data.network.api

import com.example.boka.core.BodyResult
import com.example.boka.data.network.user.body.UpdateProfileBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface UserApi {
    @PUT("me/profile")
    suspend fun updateProfile(
        @Body updateProfileBody: UpdateProfileBody
    ): Response<BodyResult<Any>>
}