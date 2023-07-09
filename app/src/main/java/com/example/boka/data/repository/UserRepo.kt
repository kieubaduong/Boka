package com.example.boka.data.repository

import com.example.boka.core.BaseRepo
import com.example.boka.core.BodyResult
import com.example.boka.data.model.NetworkResult
import com.example.boka.data.network.user.UserService
import com.example.boka.data.network.user.body.UpdateProfileBody

class UserRepo(private val userService: UserService) : BaseRepo() {
    suspend fun updateProfile(updateProfileBody: UpdateProfileBody) : NetworkResult<Any> {
        val res : NetworkResult<BodyResult<Any>> = userService.updateProfile(updateProfileBody)
        return handleNetworkResult(res) { it }
    }
}