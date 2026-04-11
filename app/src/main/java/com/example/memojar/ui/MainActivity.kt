package com.example.memojar.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

/**
 * MainActivity is the entry point of the app — the first screen Android launches.
 *
 * In modern Android development, we use one Activity and navigate
 * between different Compose screens (instead of having multiple Activities).
 * Each Compose screen serves the same purpose as a traditional Activity.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Show the splash screen while the app loads
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // Set the Compose content — MemoJarApp is the root composable
        setContent {
            MemoJarApp()
        }
    }
}

