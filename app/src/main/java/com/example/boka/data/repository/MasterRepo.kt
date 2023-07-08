package com.example.boka.data.repository

import com.example.boka.core.BaseRepo
import com.example.boka.core.BodyResult
import com.example.boka.data.model.Genre
import com.example.boka.data.model.NetworkResult
import com.example.boka.data.network.master.MasterService

class MasterRepo(private val masterService: MasterService) : BaseRepo() {
    suspend fun getGenres() : NetworkResult<List<Genre>> {
        val res : NetworkResult<BodyResult<List<Genre>>> = masterService.getGenres()
        return handleNetworkResult(res) { it.data }
    }
}