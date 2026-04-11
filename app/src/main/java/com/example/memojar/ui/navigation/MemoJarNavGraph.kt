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

/**
 * MemoJarNavGraph defines all the screens in the app and how
 * to navigate between them.
 *
 * NavHost is like a container that swaps between different screens
 * based on the current route (a URL-like path string).
 */
@Composable
fun MemoJarNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route  // The app starts on the Home screen
    ) {
        // Home screen — shows the list of all journal entries
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        // New entry screen — creates a new journal entry
        composable(route = Screen.NewEntry.route) {
            EntryFormScreen(entryId = null, navController = navController)
        }

        // Entry detail screen — shows the full details of one entry.
        // The "entryId" argument is extracted from the route URL.
        composable(
            route = Screen.EntryDetail.route,
            arguments = listOf(navArgument("entryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getString("entryId")
            if (entryId != null) {
                EntryDetailScreen(entryId = entryId, navController = navController)
            }
        }

        // Edit entry screen — edits an existing journal entry
        composable(
            route = Screen.EditEntry.route,
            arguments = listOf(navArgument("entryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getString("entryId")
            EntryFormScreen(entryId = entryId, navController = navController)
        }
    }
}
