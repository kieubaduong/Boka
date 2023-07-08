package com.example.boka.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.example.boka.core.NormalScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController){
    navigation(
        route= Graph.AUTHENTICATION,
        startDestination = NormalScreen.Login.route,
    ){

    }
}