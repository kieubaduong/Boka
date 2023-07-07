package com.example.boka.data.model

import java.time.LocalDateTime

data class User(
    val name: String? = "",
    val email: String? = "",
    val createdAt: LocalDateTime? = LocalDateTime.MIN,
    val updatedAt: LocalDateTime? = LocalDateTime.MIN,
    val favoriteGenres : List<String> = emptyList(),
    var token : String? = null
){
    companion object {
        val NULL = User(
            name = "",
            email = "",
            createdAt = LocalDateTime.MIN,
            updatedAt = LocalDateTime.MIN
        )
    }
}
