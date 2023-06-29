package com.example.boka.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.boka.core.GlobalData
import com.example.boka.core.PreferencesKeys
import com.example.boka.core.dataStore
import com.example.boka.data.network.api.ApiService
import com.example.boka.data.network.auth.AuthService
import com.example.boka.data.model.User
import com.example.boka.data.repository.AuthRepo
import com.example.boka.ui.BaseScreen
import kotlinx.coroutines.flow.first

@Composable
fun RootNavGraph(navController: NavHostController){
    val datastore = LocalContext.current.dataStore
    val user: MutableState<User?> = remember { mutableStateOf(null) }
    val isUserNotNull = rememberUpdatedState(newValue = user.value != null)
    val loading = rememberUpdatedState(newValue = user.value != null)

    LaunchedEffect(key1 = Unit){
        val token = datastore.data.first()[PreferencesKeys.TOKEN]
        token?.let { ApiService.token = it }
        val authApi = ApiService.authApi
        val authService = AuthService(authApi)
        val authRepo = AuthRepo(authService)
        val response = authRepo.getAuth()
        user.value = response.data
        user.value?.let{ GlobalData.currentUser = it }
    }

    if(loading.value){
        if (isUserNotNull.value) {
            NavHost(
                navController = navController,
                route = Graph.ROOT,
                startDestination = Graph.BASE
            ) {
                authNavGraph(navController)
                composable(route = Graph.BASE) {
                    BaseScreen()
                }
            }
        } else {
            NavHost(
                navController = navController,
                route = Graph.ROOT,
                startDestination = Graph.AUTHENTICATION
            ) {
                authNavGraph(navController)
                composable(route = Graph.BASE) {
                    BaseScreen()
                }
            }
        }
    }


}