package com.example.boka.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boka.data.model.Book
import com.example.boka.data.repository.BookRepo
import com.example.boka.util.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(private val bookRepo: BookRepo, bookId: Int) : ViewModel() {
    private val _getBookDetailResult = MutableStateFlow<ApiResult<Book>>(ApiResult.Loading)
    val getBookDetailResult: StateFlow<ApiResult<Book>> get() = _getBookDetailResult

    init {
        getBookDetail(bookId)
    }

    private fun getBookDetail(bookId: Int) {
        viewModelScope.launch {
            _getBookDetailResult.value = ApiResult.Loading
            try {
                val response = bookRepo.getBookDetail(bookId)
                val book = response.data
                val success = response.error == null && book != null
                if (success) {
                    _getBookDetailResult.value = ApiResult.Success(book ?: Book.NULL)
                } else {
                    val errorBody = response.error
                    _getBookDetailResult.value = ApiResult.Error(Exception(errorBody))
                }
            } catch (e: Exception) {
                _getBookDetailResult.value = ApiResult.Error(e)
            }
        }
    }

}