package com.example.bookreadingapplication.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookreadingapplication.core.BottomBarScreen
import com.example.bookreadingapplication.presentation.HomeScreen
import com.example.bookreadingapplication.presentation.ProfileScreen
import com.example.bookreadingapplication.presentation.SavedBookScreen
import com.example.bookreadingapplication.presentation.history.HistoryScreen

@Composable
fun BaseNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        route= Graph.BASE,
        startDestination = BottomBarScreen.Home.route,
    ){
        composable(BottomBarScreen.Home.route) { HomeScreen(navController) }
        composable(BottomBarScreen.SavedBook.route) { SavedBookScreen(navController) }
        composable(BottomBarScreen.Profile.route) { ProfileScreen(navController) }
        composable(BottomBarScreen.History.route) { HistoryScreen(navController) }
        detailNavGraph(navController)
        searchNavGraph(navController)
    }
}