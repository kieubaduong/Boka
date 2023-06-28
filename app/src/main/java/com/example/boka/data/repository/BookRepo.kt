package com.example.boka.data.repository

import com.example.boka.core.BaseRepo
import com.example.boka.data.data_source.network.book.BookService
import com.example.boka.data.data_source.network.book.result.BookJson
import com.example.boka.data.model.Book
import com.example.boka.data.model.NetworkResult

class BookRepo(private val bookService: BookService) : BaseRepo() {
    suspend fun getTopRatedBooks(): NetworkResult<List<Book>> {
        val res: NetworkResult<List<BookJson>> = bookService.getTopRatedBooks()
        return handleNetworkResult(res) { it.map { bookJson -> bookJson.toBook() } }
    }
}