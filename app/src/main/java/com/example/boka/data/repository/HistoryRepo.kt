package com.example.boka.data.repository

import com.example.boka.core.BaseRepo
import com.example.boka.core.BodyResult
import com.example.boka.data.model.NetworkResult
import com.example.boka.data.network.history.HistoryService

class HistoryRepo(private val historyService: HistoryService) : BaseRepo() {
    suspend fun getUserRating(): NetworkResult<Map<String,Int>> {
        val res : NetworkResult<BodyResult<Map<String, Int>>> = historyService.getUserRating()
        return handleNetworkResult(res) { it.data }
    }
}