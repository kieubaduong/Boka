package com.example.boka.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.boka.core.BottomBarScreen
import com.example.boka.core.GlobalData
import com.example.boka.core.NormalScreen
import com.example.boka.core.PreferencesKeys
import com.example.boka.core.dataStore
import com.example.boka.data.model.User
import com.example.boka.data.network.api.ApiService
import com.example.boka.data.network.auth.AuthService
import com.example.boka.data.repository.AuthRepo
import com.example.boka.ui.ProfileScreen
import com.example.boka.ui.SavedBookScreen
import com.example.boka.ui.auth.SignInScreen
import com.example.boka.ui.favourite_genres.FavouriteGenreScreen
import com.example.boka.ui.history.HistoryScreen
import com.example.boka.ui.home.HomeScreen
import kotlinx.coroutines.flow.first

@Composable
fun BaseNavGraph(navController: NavHostController){
    val datastore = LocalContext.current.dataStore
    val user: MutableState<User?> = remember { mutableStateOf(null) }

    LaunchedEffect(key1 = Unit){
        val token = datastore.data.first()[PreferencesKeys.TOKEN]
        token?.let {
            ApiService.token = it
            GlobalData.isLoggedIn.value = true
        }
        val authApi = ApiService.authApi
        val authService = AuthService(authApi)
        val authRepo = AuthRepo(authService)
        val response = authRepo.getAuth()
        user.value = response.data
        user.value?.let{ GlobalData.currentUser = it }
    }

    val isLoggedIn = GlobalData.isLoggedIn.value

    NavHost(
        navController = navController,
        route= Graph.BASE,
        startDestination = if (!isLoggedIn) NormalScreen.Login.route else if(user.value?.favoriteGenres?.isEmpty() == true) NormalScreen.FavouriteGenre.route else BottomBarScreen.Home.route
    ){
        composable(BottomBarScreen.Home.route) { HomeScreen(navController) }
        composable(BottomBarScreen.SavedBook.route) { SavedBookScreen(navController) }
        composable(BottomBarScreen.Profile.route) { ProfileScreen(navController) }
        composable(BottomBarScreen.History.route) { HistoryScreen(navController) }
        detailNavGraph(navController)
        searchNavGraph(navController)
        composable(NormalScreen.Login.route){
            SignInScreen(navController)
        }
        composable(NormalScreen.FavouriteGenre.route){
            FavouriteGenreScreen(navController)
        }
    }
}