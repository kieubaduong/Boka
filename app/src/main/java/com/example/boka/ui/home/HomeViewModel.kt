package com.example.boka.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaquo.python.Python
import com.example.boka.data.model.Book
import com.example.boka.data.repository.BookRepo
import com.example.boka.data.repository.HistoryRepo
import com.example.boka.util.ApiResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val bookRepo: BookRepo, private val historyRepo: HistoryRepo) : ViewModel() {

    private val _topRatedBooks = MutableStateFlow<ApiResult<List<Book>>>(ApiResult.Loading)
    val topRatedBooks: StateFlow<ApiResult<List<Book>>> get() = _topRatedBooks
    private val _userBasedBooks = MutableStateFlow<ApiResult<List<Book>>>(ApiResult.Loading)
    val userBasedBooks: StateFlow<ApiResult<List<Book>>> get() = _userBasedBooks
    private var userRating : String? = null
    private val _recentlyViewedBooks = MutableStateFlow<ApiResult<List<Book>>>(ApiResult.Loading)
    val recentlyViewedBooks: StateFlow<ApiResult<List<Book>>> get() = _recentlyViewedBooks
    init {
        getTopRatedBooks()
        getUserBasedBooks()
        getRecentlyViewedBooks()
    }

    private suspend fun getUserRating(){
        return withContext(Dispatchers.IO){
            try{
                val res = historyRepo.getUserRating()
                if(res.data != null){
                    userRating = Gson().toJson(res.data)
                    userRating = userRating.toString().replace("\"", "\\\"")
                    Log.d("alo alo", userRating.toString())
                }
                else{
                    userRating = null
                }
            }
            catch (e : Exception) {
                userRating = null
            }
        }
    }
    private fun getUserBasedBooks(){
        viewModelScope.launch {
            withContext(Dispatchers.Default) { getUserRating() }
            val py = Python.getInstance()
            val module = py.getModule("user_based_model")
            var isbns: String? = null

            if(userRating == null || userRating == "{}"){
                _userBasedBooks.value = ApiResult.Success(emptyList())
                return@launch
            }

            userRating.let {
                ratings ->
                val isbnOfUserBasedBooks = module["main"]?.call("\"$ratings\"")
                var data = isbnOfUserBasedBooks.toString()
                data = data.replace("[", "").replace("]", "")
                isbns = data.split(", ").map { it.replace("\"", "") }.joinToString(separator = ",") { it }
            }

            isbns?.let {
                try {
                    val res = bookRepo.getBooks(it)
                    if(res.data != null){
                        _userBasedBooks.value = ApiResult.Success(res.data)
                    }
                    else{
                        _userBasedBooks.value = ApiResult.Error(Exception(res.error))
                    }
                }
                catch (e: Exception) {
                    _userBasedBooks.value = ApiResult.Error(Exception("ViewModel layer: ${e.message}"))
                }
                return@launch
            }
            _userBasedBooks.value = ApiResult.Error(Exception("ViewModel layer: ${"No user rating"}"))
        }
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
    private fun getRecentlyViewedBooks() {
        viewModelScope.launch {
            try{
                val res = bookRepo.getRecentlyViewedBooks()
                if(res.data != null){
                    _recentlyViewedBooks.value = ApiResult.Success(res.data)
                }
                else{
                    _recentlyViewedBooks.value = ApiResult.Error(Exception(res.error))
                }
            } catch (e: Exception) {
                _recentlyViewedBooks.value = ApiResult.Error(Exception("ViewModel layer: ${e.message}"))
            }
        }
    }
}