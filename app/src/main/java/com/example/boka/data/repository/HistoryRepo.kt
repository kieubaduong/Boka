package com.example.boka.data.repository

import com.example.boka.core.BaseRepo
import com.example.boka.core.BodyResult
import com.example.boka.data.model.Book
import com.example.boka.data.model.NetworkResult
import com.example.boka.data.network.history.HistoryService
import com.example.boka.data.network.history.result.BookRatingResult

class HistoryRepo(private val historyService: HistoryService) : BaseRepo() {
    suspend fun getUserRating(): NetworkResult<Map<String,Int>> {
        val res : NetworkResult<BodyResult<Map<String, Int>>> = historyService.getUserRating()
        return handleNetworkResult(res) { it.data }
    }
    suspend fun getRatedBooks(perPage: Int): NetworkResult<List<Book>> {
        val res: NetworkResult<BodyResult<List<BookRatingResult>>> = historyService.getRatedBooks(perPage)
        return handleNetworkResult(res) { it.data.map { bookJson -> bookJson.toBook() } }
    }
}