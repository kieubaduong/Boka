package com.example.boka.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.boka.data.model.User

object GlobalData {
    lateinit var currentUser: User
    var isLoggedIn: MutableState<Boolean> = mutableStateOf(false)
}