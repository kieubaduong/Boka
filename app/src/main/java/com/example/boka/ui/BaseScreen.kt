package com.example.boka.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.boka.core.BottomBarScreen
import com.example.boka.navigation.BaseNavGraph
import com.example.boka.ui.theme.AppColor

@Composable
fun BaseScreen() {
    val navController = rememberNavController()
    val items = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.SavedBook,
        BottomBarScreen.History,
        BottomBarScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = items.any { it.route == currentDestination?.route }
    Scaffold(
        bottomBar = {
            if (bottomBarDestination) {
                BottomNavigation(
                    backgroundColor = Color.White,
                ) {
                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                if (currentDestination != null) {
                                    Icon(
                                        painterResource(id = screen.icon),
                                        contentDescription = null,
                                        modifier = Modifier.size(30.dp),
                                        tint = if (currentDestination.route == screen.route) AppColor.pink else AppColor.grey,
                                    )
                                }
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.popBackStack()
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }

        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BaseNavGraph(navController = navController)
        }
    }
}