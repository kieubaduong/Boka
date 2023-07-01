package com.example.boka.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaquo.python.Python
import com.example.boka.data.model.Book
import com.example.boka.data.repository.BookRepo
import com.example.boka.util.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val bookRepo: BookRepo) : ViewModel() {

    private val _topRatedBooks = MutableStateFlow<ApiResult<List<Book>>>(ApiResult.Loading)
    val topRatedBooks: StateFlow<ApiResult<List<Book>>> get() = _topRatedBooks

    init {
        getTopRatedBooks()
        getUserBasedBooks()
    }

    private fun getUserBasedBooks(){
        val py = Python.getInstance()
        val module = py.getModule("user_based_model")
        val userBasedBooks = module["get_user_based_books"]?.call("{\"0553265865\":4,\"1400062888\":5,\"071484103X\":3}")
        Log.d("Python model", userBasedBooks.toString())
    }

    private fun getTopRatedBooks() {
        viewModelScope.launch {
            try{
                val res = bookRepo.getTopRatedBooks()
                if(res.data != null){
                    _topRatedBooks.value = ApiResult.Success(res.data)
                }
                else{
                    _topRatedBooks.value = ApiResult.Error(Exception(res.error))
                }
            } catch (e: Exception) {
                _topRatedBooks.value = ApiResult.Error(Exception("ViewModel layer: ${e.message}"))
            }
        }
    }
}