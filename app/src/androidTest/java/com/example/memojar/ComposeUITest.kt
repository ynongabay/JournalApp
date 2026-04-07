package com.example.memojar

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.memojar.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ComposeUITest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun createNewEntry_appearsInList() {
        composeTestRule.onNodeWithContentDescription("Add Entry").performClick()

        composeTestRule.onNodeWithText("Title").performTextInput("My Test Entry")
        composeTestRule.onNodeWithText("Content").performTextInput("This is a UI test.")

        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText("My Test Entry").assertIsDisplayed()
    }
}
