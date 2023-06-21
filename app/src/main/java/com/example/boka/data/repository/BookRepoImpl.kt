package com.example.boka.data.repository

import com.example.boka.data.data_source.network.book.BookService
import com.example.boka.data.model.Book
import com.example.boka.data.model.Response
import com.example.boka.domain.entity.BookEntity
import com.example.boka.domain.mapper.BookMapper

class BookRepoImpl(private val bookService: BookService) {
    suspend fun getTopRatedBooks() : Response<List<BookEntity>>{
        val res : Response<List<Book>> = bookService.getTopRatedBooks()
        try {
            res.data?.let{
                topRatedBooks ->
                val bookEntities = topRatedBooks.map { BookMapper.mapToEntity(it) }
                return Response(data = bookEntities)
            }
            return Response(error = res.error)
        }
        catch (e: Exception) {
            return Response(error = e.message ?: "Unknown error occurred")
        }
    }
}