package com.example.boka.data.network.user

import com.example.boka.core.BaseService
import com.example.boka.data.network.api.UserApi
import com.example.boka.data.network.user.body.UpdateProfileBody

class UserService(private val userApi: UserApi) : BaseService() {
    suspend fun updateProfile(updateProfileBody: UpdateProfileBody) = callApi { userApi.updateProfile(updateProfileBody) }
}