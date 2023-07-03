package com.example.boka.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.boka.core.NormalScreen
import com.example.boka.ui.detail.BookDetailScreen

fun NavGraphBuilder.detailNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAIL,
        startDestination = "${NormalScreen.BookDetail.route}/{bookId}&{isbn}",
    ) {
        composable(
            "${NormalScreen.BookDetail.route}/{bookId}&{isbn}",
            arguments = listOf(
                navArgument("bookId") { type = NavType.IntType },
                navArgument("isbn") { type = NavType.StringType }
            )
        ) {
            val bookId = it.arguments?.getInt("bookId")
            val isbn = it.arguments?.getString("isbn")
            if (isbn != null) {
                BookDetailScreen(navController, bookId, isbn)
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "Isbn null")
                }
            }
        }
    }
}