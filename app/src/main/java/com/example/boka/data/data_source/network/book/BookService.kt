package com.example.boka.data.data_source.network.book

import com.example.boka.core.BaseService
import com.example.boka.data.data_source.network.api.BookApi

class BookService(private val bookApi : BookApi) : BaseService(){
    suspend fun getTopRatedBooks() = callApi { bookApi.getTopRatedBooks() }
}