package com.example.boka.data.repository

import com.example.boka.data.data_source.network.book.BookService
import com.example.boka.data.model.Book
import com.example.boka.data.model.NetworkResult
import com.example.boka.domain.entity.BookEntity
import com.example.boka.domain.mapper.BookMapper

class BookRepoImpl(private val bookService: BookService) {
    suspend fun getTopRatedBooks(): NetworkResult<List<BookEntity>> {
        val res: NetworkResult<List<Book>> = bookService.getTopRatedBooks()
        return try {
            res.data?.let { topRatedBooks ->
                topRatedBooks.map { BookMapper.mapToEntity(it) }.apply {
                    return NetworkResult(
                        data = this,
                        message = res.message,
                    )
                }

            }
            NetworkResult(error = res.error)
        } catch (e: Exception) {
            NetworkResult(error = e.message ?: "Unknown error occurred")
        }
    }
}