package com.example.memojar.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object NewEntry : Screen("new_entry")
    object Search : Screen("search")
    
    object EntryDetail : Screen("entry_detail/{entryId}") {
        fun createRoute(entryId: String): String = "entry_detail/$entryId"
    }
    
    object EditEntry : Screen("edit_entry/{entryId}") {
        fun createRoute(entryId: String): String = "edit_entry/$entryId"
    }
}
