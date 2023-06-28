package com.example.boka.data.repository

import com.example.boka.core.BaseRepo
import com.example.boka.data.data_source.network.auth.AuthService
import com.example.boka.data.data_source.network.auth.body.SignInBody
import com.example.boka.data.data_source.network.auth.result.SignInResult
import com.example.boka.data.model.NetworkResult
import com.example.boka.data.model.User

class AuthRepo(private val authService: AuthService) : BaseRepo() {
    suspend fun signIn(signInBody: SignInBody): NetworkResult<User> {
        val res: NetworkResult<SignInResult> = authService.signIn(signInBody)
        return handleNetworkResult(res) { it.toUser() }
    }

}