package com.example.boka.data.repository

import com.example.boka.data.data_source.network.auth.AuthService
import com.example.boka.data.data_source.network.auth.body.SignInBody
import com.example.boka.data.data_source.network.auth.result.SignInResult
import com.example.boka.data.model.Response
import com.example.boka.domain.entity.UserEntity
import com.example.boka.domain.mapper.UserMapper

class AuthRepoImpl(private val authService: AuthService){
    suspend fun signIn(signInBody: SignInBody): Response<UserEntity> {
        val res : Response<SignInResult> = authService.signIn(signInBody)
        try {
            res.data?.let {
                return Response(data = UserMapper.mapToEntity(it))
            }
            return Response(error = res.error)
        } catch (e: Exception) {
            return Response(error = e.message ?: "Unknown error occurred")
        }
    }

}
