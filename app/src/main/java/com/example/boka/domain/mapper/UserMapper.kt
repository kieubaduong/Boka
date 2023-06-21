package com.example.boka.domain.mapper

import com.example.boka.data.data_source.network.auth.result.SignInResult
import com.example.boka.domain.entity.UserEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object UserMapper {
    fun mapToEntity(signInResult: SignInResult) : UserEntity {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return UserEntity(
            email = signInResult.email,
            createdAt = signInResult.createdAt?.let { LocalDateTime.parse(it, formatter) } ?: LocalDateTime.now(),
            updatedAt = signInResult.updatedAt?.let { LocalDateTime.parse(it, formatter) } ?: LocalDateTime.now(),
            name = signInResult.name,
            genres = signInResult.favouriteGenres.map { it.name },
        )
    }
}