package com.example.bookreadingapplication.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookreadingapplication.core.BaseScreen

@Composable
fun RootNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION,
    ){
        authNavGraph(navController)
        composable(route = Graph.BASE){
            BaseScreen()
        }
    }
}