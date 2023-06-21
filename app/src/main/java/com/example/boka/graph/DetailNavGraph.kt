package com.example.boka.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.boka.ui.detail.BookDetailScreen

fun NavGraphBuilder.detailNavGraph(navController: NavHostController){
    navigation(
        route= Graph.DETAIL,
        startDestination = NormalScreen.BookDetail.route,
    ){
        composable(NormalScreen.BookDetail.route){
            BookDetailScreen(navController)
        }
    }
}