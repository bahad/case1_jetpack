package com.example.coffeeapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.presentation.coffee_detail.CoffeeDetailScreen
import com.example.myapplication.presentation.coffee_list.CoffeeListScreen


@Composable
fun NavGraphs() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "coffee_list") {
        composable("coffee_list") {
            CoffeeListScreen(navController)
        }
        composable(
            "coffee_detail/{coffeeId}",
            arguments = listOf(navArgument("coffeeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val coffeeId = backStackEntry.arguments?.getInt("coffeeId") ?: 0
            CoffeeDetailScreen(coffeeId, navController)
        }
    }
}