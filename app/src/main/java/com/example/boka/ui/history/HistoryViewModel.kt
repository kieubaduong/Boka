package com.example.boka.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boka.data.model.Book
import com.example.boka.data.repository.HistoryRepo
import com.example.boka.util.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val historyRepo: HistoryRepo) : ViewModel() {
    private val _ratedBooks = MutableStateFlow<ApiResult<List<Book>>>(ApiResult.Loading)
    val ratedBooks: StateFlow<ApiResult<List<Book>>> get() = _ratedBooks

    init {
        getRatedBooks(10)
    }

    private fun getRatedBooks(perPage : Int) {
        viewModelScope.launch {
            try{
                val res = historyRepo.getRatedBooks(perPage)
                if(res.data != null){
                    _ratedBooks.value = ApiResult.Success(res.data)
                }
                else{
                    _ratedBooks.value = ApiResult.Error(Exception(res.error))
                }
            } catch (e: Exception) {
                _ratedBooks.value = ApiResult.Error(Exception("ViewModel layer: ${e.message}"))
            }
        }
    }
}