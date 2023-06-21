package com.example.boka.domain.use_case

import com.example.boka.data.data_source.network.auth.body.SignInBody
import com.example.boka.data.model.Response
import com.example.boka.data.repository.AuthRepoImpl
import com.example.boka.domain.entity.UserEntity

class SignInUseCase(private val authRepo: AuthRepoImpl) {
    suspend operator fun invoke(email: String, password: String): Response<UserEntity> {
        return authRepo.signIn(SignInBody(email, password))
    }
}