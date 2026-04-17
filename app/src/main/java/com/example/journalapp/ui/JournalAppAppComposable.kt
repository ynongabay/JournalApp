package com.example.journalapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.journalapp.ui.navigation.JournalAppNavGraph
import com.example.journalapp.ui.theme.JournalAppTheme

// Root composable — wraps the app in the theme and sets up navigation
@Composable
fun JournalAppApp() {
    JournalAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            JournalAppNavGraph(navController = navController)
        }
    }
}

