package com.example.boka.data.repository

import com.example.boka.data.data_source.network.auth.AuthService
import com.example.boka.data.data_source.network.auth.body.SignInBody
import com.example.boka.data.data_source.network.auth.result.SignInResult
import com.example.boka.data.model.NetworkResult
import com.example.boka.domain.entity.UserEntity
import com.example.boka.domain.mapper.UserMapper

class AuthRepoImpl(private val authService: AuthService) {
    suspend fun signIn(signInBody: SignInBody): NetworkResult<UserEntity> {
        val res: NetworkResult<SignInResult> = authService.signIn(signInBody)
        return try {
            res.data?.let {
                return NetworkResult(
                    data = UserMapper.mapToEntity(it),
                    message = res.message,
                    token = res.token,
                    error = res.error
                )
            }
            NetworkResult(error = res.error)
        } catch (e: Exception) {
            NetworkResult(error = e.message ?: "Unknown error occurred")
        }
    }

}
