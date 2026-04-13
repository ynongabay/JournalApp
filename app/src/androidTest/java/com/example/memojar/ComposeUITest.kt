package com.example.memojar

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.memojar.ui.MainActivity
import org.junit.Rule
import org.junit.Test

// UI test — runs on a real device or emulator
class ComposeUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    // Test: create a new entry and check it shows up
    @Test
    fun createNewEntry_appearsInList() {
        composeTestRule.onNodeWithContentDescription("Add Entry").performClick()
        composeTestRule.onNodeWithText("Title").performTextInput("My Test Entry")
        composeTestRule.onNodeWithText("Content").performTextInput("This is a UI test.")
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.onNodeWithText("My Test Entry").assertIsDisplayed()
    }
}

