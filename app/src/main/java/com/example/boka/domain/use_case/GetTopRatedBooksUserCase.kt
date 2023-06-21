package com.example.boka.domain.use_case

import com.example.boka.data.repository.BookRepoImpl

class GetTopRatedBooksUserCase(private val bookRepo: BookRepoImpl) {
    suspend operator fun invoke() = bookRepo.getTopRatedBooks()
}