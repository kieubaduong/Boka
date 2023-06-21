package com.example.boka.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boka.domain.entity.BookEntity
import com.example.boka.domain.use_case.GetTopRatedBooksUserCase
import com.example.boka.util.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val getTopRatedBooksUserCase: GetTopRatedBooksUserCase) : ViewModel() {

    private val _topRatedBooks = MutableStateFlow<ApiResult<List<BookEntity>>>(ApiResult.Loading)
    val topRatedBooks: StateFlow<ApiResult<List<BookEntity>>> get() = _topRatedBooks

    init {
        getTopRatedBooks()
        Log.d("Debug", "HomeVM: init")
    }

    private fun getTopRatedBooks() {
        viewModelScope.launch {
            try{
                val res = getTopRatedBooksUserCase()
                if(res.data != null){
                    _topRatedBooks.value = ApiResult.Success(res.data)
                }
                else{
                    _topRatedBooks.value = ApiResult.Error(Exception(res.error))
                }
            } catch (e: Exception) {
                _topRatedBooks.value = ApiResult.Error(e)
            }
        }
    }
}