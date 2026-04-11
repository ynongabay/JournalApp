package com.example.memojar.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.memojar.ui.navigation.MemoJarNavGraph
import com.example.memojar.ui.theme.MemoJarTheme

/**
 * MemoJarApp is the root composable function.
 * It wraps the entire app in the custom theme and sets up navigation.
 */
@Composable
fun MemoJarApp() {
    // Apply the custom MemoJar theme (colors, fonts, shapes)
    MemoJarTheme {
        // Surface provides the background color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Create the navigation controller that manages screen transitions
            val navController = rememberNavController()

            // Set up the navigation graph (defines which screens exist)
            MemoJarNavGraph(navController = navController)
        }
    }
}
