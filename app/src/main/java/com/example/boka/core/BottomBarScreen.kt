package com.example.boka.core

import com.example.boka.R

sealed class BottomBarScreen(val route: String, val icon: Int) {
    data class Home(private val genres : List<Int>? = null) : BottomBarScreen("home", R.drawable.ic_home)
    object SavedBook : BottomBarScreen("save", R.drawable.ic_save)
    object Profile : BottomBarScreen("profile", R.drawable.ic_profile)
    object History : BottomBarScreen("history", R.drawable.ic_history)
}
