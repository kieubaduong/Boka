package com.example.boka.data.data_source.network.auth

import com.example.boka.core.BaseService
import com.example.boka.data.data_source.network.api.AuthApi
import com.example.boka.data.data_source.network.auth.body.SignInBody
import com.example.boka.data.data_source.network.auth.result.SignInResult
import com.example.boka.data.model.NetworkResult

class AuthService(private val authApi: AuthApi) : BaseService() {
    suspend fun signIn(signInBody: SignInBody): NetworkResult<SignInResult> {
        return callApi { authApi.signIn(signInBody) }
    }
}