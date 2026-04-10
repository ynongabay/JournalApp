package com.example.memojar.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.memojar.ui.screens.EntryDetailScreen
import com.example.memojar.ui.screens.EntryFormScreen
import com.example.memojar.ui.screens.EntryListScreen
import com.example.memojar.ui.screens.HomeScreen

import com.example.memojar.ui.screens.SearchScreen

@Composable
fun MemoJarNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { fadeIn(tween(300)) },
        exitTransition = { fadeOut(tween(300)) },
        popEnterTransition = { fadeIn(tween(300)) },
        popExitTransition = { fadeOut(tween(300)) }
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.EntryList.route) {
            EntryListScreen(navController = navController)
        }


        composable(route = Screen.NewEntry.route) {
            EntryFormScreen(entryId = null, navController = navController)
        }
        
        composable(
            route = Screen.EntryDetail.route,
            arguments = listOf(navArgument("entryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getString("entryId")
            if (entryId != null) {
                EntryDetailScreen(entryId = entryId, navController = navController)
            }
        }

        composable(
            route = Screen.EditEntry.route,
            arguments = listOf(navArgument("entryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getString("entryId")
            EntryFormScreen(entryId = entryId, navController = navController)
        }
        
        composable(route = Screen.Search.route) {
            SearchScreen(navController = navController)
        }
    }
}
