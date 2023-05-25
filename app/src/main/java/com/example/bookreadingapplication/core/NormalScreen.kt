package com.example.bookreadingapplication.core

import com.example.bookreadingapplication.R

sealed class NormalScreen(val route: String) {
    object BookDetail : NormalScreen("book_detail")
    object Search: NormalScreen("search")
    object Login : NormalScreen("login")
}