package com.example.memojar.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.memojar.ui.navigation.MemoJarNavGraph
import com.example.memojar.ui.theme.MemoJarTheme

// Root composable — wraps the app in the theme and sets up navigation
@Composable
fun MemoJarApp() {
    MemoJarTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            MemoJarNavGraph(navController = navController)
        }
    }
}

