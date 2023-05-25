package com.example.bookreadingapplication.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bookreadingapplication.core.NormalScreen
import com.example.bookreadingapplication.presentation.SearchScreen

fun NavGraphBuilder.searchNavGraph(navController: NavHostController){
    navigation(
        route= Graph.SEARCH,
        startDestination = NormalScreen.Search.route,
    ){
        composable(NormalScreen.Search.route){
            SearchScreen(navController)
        }
    }
}