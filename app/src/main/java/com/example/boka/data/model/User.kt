package com.example.boka.data.model

import java.time.LocalDateTime

data class User(
    val name: String? = "",
    val email: String? = "",
    val genres: List<String> = emptyList(),
    val createdAt: LocalDateTime? = LocalDateTime.MIN,
    val updatedAt: LocalDateTime? = LocalDateTime.MIN,
    val favoriteGenres : List<Int> = emptyList(),
    var token : String? = null
){
    companion object {
        val NULL = User(
            name = "",
            email = "",
            genres = emptyList(),
            createdAt = LocalDateTime.MIN,
            updatedAt = LocalDateTime.MIN
        )
    }
}
