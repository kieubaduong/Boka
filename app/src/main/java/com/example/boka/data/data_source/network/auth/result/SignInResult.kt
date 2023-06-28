package com.example.boka.data.data_source.network.auth.result

import com.example.boka.core.BodyResult
import com.example.boka.data.model.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SignInResult(data : UserJson, token : String) : BodyResult<UserJson>(data, token = token){
    fun toUser() : User {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return User(
            name = data.name,
            email = data.email,
            createdAt = data.let {
                LocalDateTime.parse(it.createdAt , formatter)
            } ?: LocalDateTime.MIN,
            updatedAt = data.let {
                LocalDateTime.parse(it.updatedAt , formatter)
            } ?: LocalDateTime.MIN,
            token = token ?: "",
        )
    }
}