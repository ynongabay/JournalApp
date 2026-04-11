package com.example.memojar

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.memojar.ui.MainActivity
import org.junit.Rule
import org.junit.Test

/**
 * ComposeUITest tests the app's user interface by simulating
 * real user interactions (clicking buttons, typing text) and
 * checking that the expected content appears on screen.
 *
 * These are "instrumented tests" — they run on a real device or emulator.
 */
class ComposeUITest {

    // This rule launches the MainActivity so we can interact with the UI
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Test: creating a new entry and verifying it appears in the list.
     */
    @Test
    fun createNewEntry_appearsInList() {
        // Tap the "Add Entry" floating action button
        composeTestRule.onNodeWithContentDescription("Add Entry").performClick()

        // Type a title into the Title field
        composeTestRule.onNodeWithText("Title").performTextInput("My Test Entry")

        // Type content into the Content field
        composeTestRule.onNodeWithText("Content").performTextInput("This is a UI test.")

        // Tap the Save button
        composeTestRule.onNodeWithText("Save").performClick()

        // Verify that the new entry title appears on the home screen
        composeTestRule.onNodeWithText("My Test Entry").assertIsDisplayed()
    }
}

