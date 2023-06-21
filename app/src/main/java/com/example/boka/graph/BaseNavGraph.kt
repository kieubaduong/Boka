package com.example.boka.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.boka.ui.home.HomeScreen
import com.example.boka.ui.ProfileScreen
import com.example.boka.ui.SavedBookScreen
import com.example.boka.ui.history.HistoryScreen

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