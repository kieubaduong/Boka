package com.example.boka.ui.favourite_genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boka.data.model.Genre
import com.example.boka.data.repository.MasterRepo
import com.example.boka.util.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouriteGenresViewModel(private val masterRepo: MasterRepo):ViewModel() {
    private val _genres = MutableStateFlow<ApiResult<List<Genre>>>(ApiResult.Loading)
    val genres: StateFlow<ApiResult<List<Genre>>> get() = _genres

    init {
        getAllGenre()
    }

    private fun getAllGenre() {
        viewModelScope.launch {
            try{
                val res = masterRepo.getGenres()
                if(res.data != null){
                    _genres.value = ApiResult.Success(res.data)
                }
                else{
                    _genres.value = ApiResult.Error(Exception(res.error))
                }
            } catch (e: Exception) {
                _genres.value = ApiResult.Error(Exception("ViewModel layer: ${e.message}"))
            }
        }
    }

}