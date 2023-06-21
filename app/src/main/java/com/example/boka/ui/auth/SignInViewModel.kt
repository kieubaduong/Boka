package com.example.boka.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boka.core.GlobalData
import com.example.boka.data.data_source.network.ApiService
import com.example.boka.domain.entity.UserEntity
import com.example.boka.domain.use_case.SignInUseCase
import com.example.boka.util.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _signInResult = MutableStateFlow<ApiResult<UserEntity>>(ApiResult.Loading)
    val signInResult: StateFlow<ApiResult<UserEntity>> get() = _signInResult
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _signInResult.value = ApiResult.Loading
            try {
                val response = signInUseCase(email, password)
                val user = response.data
                val success = response.error == null && user != null
                if (success) {
                    _signInResult.value = ApiResult.Success(user ?: UserEntity.NULL)
                    ApiService.token = response.token ?: "Null token"
                    GlobalData.currentUser = user ?: UserEntity.NULL
                } else {
                    val errorBody = response.error
                    _signInResult.value = ApiResult.Error(Exception(errorBody))
                }
            } catch (e: Exception) {
                _signInResult.value = ApiResult.Error(e)
            }
        }
    }
}