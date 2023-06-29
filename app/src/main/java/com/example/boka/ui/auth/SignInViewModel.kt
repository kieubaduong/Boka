package com.example.boka.ui.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.boka.core.GlobalData
import com.example.boka.core.PreferencesKeys
import com.example.boka.data.model.User
import com.example.boka.data.network.api.ApiService
import com.example.boka.data.network.auth.body.SignInBody
import com.example.boka.data.repository.AuthRepo
import com.example.boka.util.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepo: AuthRepo,
    private val dataStore: DataStore<Preferences>,
) : ViewModel() {

    private val _signInResult = MutableStateFlow<ApiResult<User>>(ApiResult.Loading)
    val signInResult: StateFlow<ApiResult<User>> get() = _signInResult
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _signInResult.value = ApiResult.Loading
            try {
                val response = authRepo.signIn(SignInBody(email, password))
                val user = response.data
                val success = response.error == null && user != null
                if (success) {
                    _signInResult.value = ApiResult.Success(user ?: User.NULL)
                    ApiService.token = response.data?.token ?: "Null token"
                    GlobalData.currentUser = user ?: User.NULL
                    response.data?.token?.let { token ->
                        dataStore.edit {
                            it[PreferencesKeys.TOKEN] = token
                        }
                    }
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