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
import com.example.boka.core.PreferencesKeys
import com.example.boka.core.dataStore
import com.example.boka.ui.BaseScreen
import kotlinx.coroutines.flow.first

@Composable
fun RootNavGraph(navController: NavHostController){
    val token: MutableState<String?> = remember { mutableStateOf(null) }
    val datastore = LocalContext.current.dataStore
    val isTokenReady = rememberUpdatedState(token.value != null)

    LaunchedEffect(key1 = Unit){
        token.value = datastore.data.first()[PreferencesKeys.TOKEN]
    }

    if (isTokenReady.value) {
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