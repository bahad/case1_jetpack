package com.example.coffeeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.presentation.coffee_detail.CoffeeDetailScreen
import com.example.myapplication.presentation.coffee_list.CoffeeListScreen
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.coffeeapp.presentation.favorites.FavoriteScreen

@Composable
fun NavGraphs() {
    val navController = rememberNavController()
    val bottomNavItems = listOf(NavigationDestination.CoffeeList, NavigationDestination.Favorites)

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    Scaffold(bottomBar = {
        NavigationBar {
            bottomNavItems.forEach { destination ->
                val isSelected = currentDestination?.route == destination.route
                val icon: ImageVector = when (destination) {
                    NavigationDestination.CoffeeList -> Icons.Filled.LocalCafe
                    NavigationDestination.Favorites -> Icons.Filled.Favorite
                    else -> Icons.Filled.LocalCafe
                }
                NavigationBarItem(
                    selected = isSelected,
                    icon = { Icon(icon, contentDescription = destination.title) },
                    label = { Text(text = destination.title) },
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.startDestinationRoute!!) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },


                    )
            }
        }
    }) { innerPadding ->
        NavHost(
            navController,
            startDestination = "coffee_list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationDestination.CoffeeList.route) {
                CoffeeListScreen(navController)
            }
            composable(NavigationDestination.Favorites.route) {
                FavoriteScreen(navController = navController)
            }
            composable(
                "${NavigationDestination.CoffeeDetail.route}/{coffeeId}",
                arguments = listOf(navArgument("coffeeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val coffeeId = backStackEntry.arguments?.getInt("coffeeId") ?: 0
                CoffeeDetailScreen(coffeeId, navController)
            }
        }
    }

}

sealed class NavigationDestination(val route: String, val title: String) {
    object CoffeeList : NavigationDestination("coffee_list", "coffees")
    object CoffeeDetail : NavigationDestination("coffee_detail", "coffee_detail")
    object Favorites : NavigationDestination("favorites","Favorites")
}