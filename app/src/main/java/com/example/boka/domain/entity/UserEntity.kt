package com.example.boka.domain.entity

import java.time.LocalDateTime

data class UserEntity(
    val name: String,
    val email: String,
    val genres: List<String> = emptyList(),
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
){
    companion object {
        val NULL = UserEntity(
            name = "",
            email = "",
            genres = emptyList(),
            createdAt = LocalDateTime.MIN,
            updatedAt = LocalDateTime.MIN
        )
    }
}
