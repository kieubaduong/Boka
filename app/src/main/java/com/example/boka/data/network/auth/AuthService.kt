package com.example.boka.data.network.auth

import com.example.boka.core.BaseService
import com.example.boka.core.BodyResult
import com.example.boka.data.network.api.AuthApi
import com.example.boka.data.network.auth.body.SignInBody
import com.example.boka.data.network.auth.result.SignInResult
import com.example.boka.data.network.auth.result.UserJson
import com.example.boka.data.model.NetworkResult

class AuthService(private val authApi: AuthApi) : BaseService() {
    suspend fun getAuth(): NetworkResult<BodyResult<UserJson>> {
        return callApi { authApi.getAuth() }
    }
    suspend fun signIn(signInBody: SignInBody): NetworkResult<SignInResult> {
        return callApi { authApi.signIn(signInBody) }
    }
}