package com.example.memojar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.memojar.ui.screens.EntryDetailScreen
import com.example.memojar.ui.screens.EntryFormScreen
import com.example.memojar.ui.screens.HomeScreen

// Sets up all screen routes and connects them to composables
@Composable
fun MemoJarNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Home screen
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // New entry screen
        composable(route = Screen.NewEntry.route) {
            EntryFormScreen(entryId = null, navController = navController)
        }

        // Entry detail screen — reads entryId from the route
        composable(
            route = Screen.EntryDetail.route,
            arguments = listOf(navArgument("entryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getString("entryId")
            if (entryId != null) {
                EntryDetailScreen(entryId = entryId, navController = navController)
            }
        }

        // Edit entry screen
        composable(
            route = Screen.EditEntry.route,
            arguments = listOf(navArgument("entryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getString("entryId")
            EntryFormScreen(entryId = entryId, navController = navController)
        }
    }
}

