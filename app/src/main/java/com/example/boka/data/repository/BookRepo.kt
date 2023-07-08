package com.example.boka.data.repository

import com.example.boka.core.BaseRepo
import com.example.boka.core.BodyResult
import com.example.boka.data.model.Book
import com.example.boka.data.model.NetworkResult
import com.example.boka.data.network.book.BookService
import com.example.boka.data.network.book.body.ReviewBookBody
import com.example.boka.data.network.book.result.BookJson

class BookRepo(private val bookService: BookService) : BaseRepo() {
    suspend fun getBooks(isbns: String): NetworkResult<List<Book>> {
        val res: NetworkResult<BodyResult<List<BookJson>>> = bookService.getBooks(isbns)
        return handleNetworkResult(res) { it.data.map { bookJson -> bookJson.toBook() } }
    }
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
    suspend fun getRecentlyViewedBooks(): NetworkResult<List<Book>> {
        val res: NetworkResult<BodyResult<List<BookJson>>> = bookService.getRecentlyViewedBooks()
        return handleNetworkResult(res) { it.data.map { bookJson -> bookJson.toBook() } }
    }

    suspend fun rateBook(rating: Int, bookId: Int): NetworkResult<Any> {
        val res: NetworkResult<BodyResult<Any>> = bookService.rateBook(ReviewBookBody(rating, bookId))
        return handleNetworkResult(res) { it.data }
    }
}