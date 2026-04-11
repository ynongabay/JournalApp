package com.example.memojar.ui.navigation

/**
 * Screen defines all the navigation routes (pages) in the app.
 *
 * A "sealed class" means only the objects defined here can be Screens.
 * This gives us a fixed set of screens that the compiler can check.
 * Each screen has a unique "route" string used by the navigation system.
 */
sealed class Screen(val route: String) {

    // The home screen showing all journal entries
    object Home : Screen("home")

    // The screen for creating a new entry
    object NewEntry : Screen("new_entry")

    // The screen showing the full details of a single entry.
    // {entryId} is a placeholder that gets replaced with the actual entry ID.
    object EntryDetail : Screen("entry_detail/{entryId}") {
        fun createRoute(entryId: String): String = "entry_detail/$entryId"
    }

    // The screen for editing an existing entry
    object EditEntry : Screen("edit_entry/{entryId}") {
        fun createRoute(entryId: String): String = "edit_entry/$entryId"
    }
}

