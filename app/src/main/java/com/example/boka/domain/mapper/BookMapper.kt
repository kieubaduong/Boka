package com.example.boka.domain.mapper

import com.example.boka.data.model.Book
import com.example.boka.domain.entity.BookEntity

object BookMapper {
    fun mapToEntity(book: Book): BookEntity {
        val category = book.genres.joinToString { it.name }
        return BookEntity(
            isbn = book.isbn,
            title = book.title,
            description = book.description,
            author = book.author,
            yearOfPublication = book.yearOfPublication,
            publisher = book.publisher,
            rating = book.rating,
            category = category,
            tags = book.tags,
            imageS = book.imageS,
            imageM = book.imageM,
            imageL = book.imageL
        )
    }
}
