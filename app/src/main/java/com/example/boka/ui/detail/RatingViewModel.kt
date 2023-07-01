package com.example.boka.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boka.data.repository.BookRepo
import com.example.boka.util.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RatingViewModel(private val bookRepo: BookRepo, private val bookId : Int) : ViewModel(){
    var rating: Int = 0
    private val _rateBookResult = MutableStateFlow<ApiResult<Any>>(ApiResult.Loading)
    val rateBookResult: StateFlow<ApiResult<Any>> get() = _rateBookResult

    fun rateBook(rating: Int) {
        _rateBookResult.value = ApiResult.Loading
        viewModelScope.launch {
            try {
                val response = bookRepo.rateBook(rating = rating, bookId = bookId)
                val success = response.data != null
                if (success) {
                    _rateBookResult.value = ApiResult.Success(response.data ?: Any())
                }
                else{
                    _rateBookResult.value = ApiResult.Error(Exception(response.error))
                }
            } catch (e: Exception) {
                _rateBookResult.value = ApiResult.Error(e)
            }
        }
    }
}