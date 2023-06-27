package com.example.boka.data.repository

import com.example.boka.data.data_source.network.api.BookService
import com.example.boka.data.data_source.network.book.result.BookJson
import com.example.boka.data.model.Book
import com.example.boka.data.model.NetworkResult

class BookRepoImpl(private val bookService: BookService) {
    suspend fun getTopRatedBooks(): NetworkResult<List<Book>> {
        val res: NetworkResult<List<BookJson>> = bookService.getTopRatedBooks()
        return try {
            res.data?.let { topRatedBooks ->
                topRatedBooks.map { it.toBook() }.apply {
                    return NetworkResult(
                        data = this,
                    )
                }

            }
            NetworkResult(error = res.error)
        } catch (e: Exception) {
            NetworkResult(error = e.message ?: "Unknown error occurred")
        }
    }
}