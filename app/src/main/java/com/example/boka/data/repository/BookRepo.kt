package com.example.boka.data.repository

import com.example.boka.core.BaseRepo
import com.example.boka.core.BodyResult
import com.example.boka.data.model.Book
import com.example.boka.data.model.NetworkResult
import com.example.boka.data.network.book.BookService
import com.example.boka.data.network.book.result.BookJson

class BookRepo(private val bookService: BookService) : BaseRepo() {
    suspend fun getBookDetail(bookId: Int): NetworkResult<Book> {
        val res: NetworkResult<BodyResult<BookJson>> = bookService.getBookDetail(bookId)
        return handleNetworkResult(res) { it.data.toBook() }
    }
    suspend fun getTopRatedBooks(): NetworkResult<List<Book>> {
        val res: NetworkResult<BodyResult<List<BookJson>>> = bookService.getTopRatedBooks()
        return handleNetworkResult(res) { it.data.map { bookJson -> bookJson.toBook() } }
    }
    suspend fun getContentBasedBook(bookId: Int): NetworkResult<List<Book>> {
        val res: NetworkResult<BodyResult<List<BookJson>>> = bookService.getContentBasedBook(bookId)
        return handleNetworkResult(res) { it.data.map { bookJson -> bookJson.toBook() } }
    }
}