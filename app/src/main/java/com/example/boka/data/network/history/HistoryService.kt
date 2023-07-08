package com.example.boka.data.network.history

import com.example.boka.core.BaseService
import com.example.boka.data.network.api.HistoryApi

class HistoryService(private val historyApi: HistoryApi) : BaseService() {
    suspend fun getUserRating() = callApi { historyApi.getUserRating() }
    suspend fun getRatedBooks (perPage : Int) = callApi { historyApi.getRatedBooks(perPage) }
}