package com.example.boka.data.network.book

import com.example.boka.core.BaseService
import com.example.boka.data.network.api.BookApi
import com.example.boka.data.network.book.body.ReviewBookBody

class BookService(private val bookApi : BookApi) : BaseService(){
    suspend fun getBooks(isbns : String) = callApi { bookApi.getBooks(isbns) }
    suspend fun getBookDetail(bookId : Int) = callApi { bookApi.getBookDetail(bookId) }
    suspend fun getTopRatedBooks() = callApi { bookApi.getTopRatedBooks() }
    suspend fun getContentBasedBook(bookId: Int) = callApi { bookApi.getContentBasedBook(bookId) }
    suspend fun getItemBasedBook(bookId: Int) = callApi { bookApi.getItemBasedBook(bookId) }
    suspend fun rateBook(reviewBookBody: ReviewBookBody) = callApi { bookApi.rateBook(reviewBookBody) }
    suspend fun getRecentlyViewedBooks() = callApi { bookApi.getRecentlyViewedBooks() }

}