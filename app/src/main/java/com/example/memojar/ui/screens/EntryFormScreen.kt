package com.example.memojar.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.memojar.MemoJarApp
import com.example.memojar.viewmodel.EntryViewModel

/**
 * EntryFormScreen is used for both creating new entries and editing existing ones.
 * - If entryId is null → we are creating a NEW entry
 * - If entryId has a value → we are EDITING an existing entry
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryFormScreen(
    entryId: String?,
    navController: NavController
) {
    // Get the ViewModel using our custom factory
    val app = LocalContext.current.applicationContext as MemoJarApp
    val viewModel: EntryViewModel = viewModel(factory = app.viewModelFactory)

    // If we're editing an existing entry, load its data into the form
    LaunchedEffect(entryId) {
        if (entryId != null) {
            viewModel.loadEntry(entryId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                // Show different title based on whether we're creating or editing
                title = { Text(if (entryId == null) "New entry" else "Edit entry") },
                // Back button
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                // Save button in the top bar
                actions = {
                    TextButton(onClick = {
                        viewModel.saveEntry()
                        navController.popBackStack()
                    }) {
                        Text("Save")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Scrollable form with all the input fields
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title input field
            OutlinedTextField(
                value = viewModel.title.value,
                onValueChange = { viewModel.title.value = it },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Content input field (multi-line)
            OutlinedTextField(
                value = viewModel.content.value,
                onValueChange = { viewModel.content.value = it },
                label = { Text("Content") },
                minLines = 5,
                modifier = Modifier.fillMaxWidth()
            )

            // Mood selector (row of emoji-style icons)
            MoodSelector(
                selectedMood = viewModel.mood.value,
                onMoodSelected = { viewModel.mood.value = it }
            )

            // Tag input (add/remove tags)
            TagInput(
                tags = viewModel.tags,
                onAddTag = { viewModel.tags.add(it) },
                onRemoveTag = { viewModel.tags.remove(it) }
            )
        }
    }
}

/**
 * MoodSelector displays a row of mood icons for the user to pick from.
 * The currently selected mood is highlighted with a filled background.
 */
@Composable
fun MoodSelector(selectedMood: String, onMoodSelected: (String) -> Unit) {
    // Map each mood name to its corresponding Material icon
    val moods = listOf(
        "awful" to Icons.Default.SentimentVeryDissatisfied,
        "sad" to Icons.Default.SentimentDissatisfied,
        "neutral" to Icons.Default.SentimentNeutral,
        "good" to Icons.Default.SentimentSatisfied,
        "happy" to Icons.Default.SentimentVerySatisfied
    )

    Column {
        Text("Mood", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            moods.forEach { (moodName, icon) ->
                if (selectedMood == moodName) {
                    // Selected mood — show with a filled/highlighted background
                    FilledTonalIconButton(onClick = { onMoodSelected(moodName) }) {
                        Icon(imageVector = icon, contentDescription = moodName)
                    }
                } else {
                    // Unselected mood — show as a plain icon button
                    IconButton(onClick = { onMoodSelected(moodName) }) {
                        Icon(imageVector = icon, contentDescription = moodName)
                    }
                }
            }
        }
    }
}

/**
 * TagInput lets users type and add tags to their journal entry.
 * Tags appear as removable chips below the text field.
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TagInput(
    tags: List<String>,
    onAddTag: (String) -> Unit,
    onRemoveTag: (String) -> Unit
) {
    // Local state for what the user is currently typing
    var tagText by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = tagText,
            onValueChange = { tagText = it },
            label = { Text("Add Tag") },
            modifier = Modifier.fillMaxWidth(),
            // Show a "Done" button on the keyboard instead of "Enter"
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            // When the user presses "Done", add the tag
            keyboardActions = KeyboardActions(
                onDone = {
                    // Only add if the text is not blank and not a duplicate
                    if (tagText.isNotBlank() && !tags.contains(tagText.trim())) {
                        onAddTag(tagText.trim())
                    }
                    tagText = ""  // Clear the input field
                }
            ),
            singleLine = true
        )

        // Display existing tags as removable chips
        if (tags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            // FlowRow wraps chips to the next line when they don't fit
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                tags.forEach { tag ->
                    InputChip(
                        selected = false,
                        onClick = { },
                        label = { Text(tag) },
                        // X button to remove the tag
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove tag",
                                modifier = Modifier
                                    .size(InputChipDefaults.IconSize)
                                    .clickable { onRemoveTag(tag) }
                            )
                        }
                    )
                }
            }
        }
    }
}

