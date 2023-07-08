package com.example.boka.data.network.master

import com.example.boka.core.BaseService
import com.example.boka.data.network.api.MasterApi

class MasterService(private val masterApi: MasterApi) : BaseService() {
    suspend fun getGenres() = callApi { masterApi.getGenres() }
}