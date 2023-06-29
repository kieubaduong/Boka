package com.example.boka.data.network.book

import com.example.boka.core.BaseService
import com.example.boka.data.network.api.BookApi

class BookService(private val bookApi : BookApi) : BaseService(){
    suspend fun getBookDetail(bookId : Int) = callApi { bookApi.getBookDetail(bookId) }
    suspend fun getTopRatedBooks() = callApi { bookApi.getTopRatedBooks() }
}