package com.example.memojar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.memojar.MemoJarApp
import com.example.memojar.ui.navigation.Screen
import com.example.memojar.viewmodel.EntryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * EntryDetailScreen shows the full details of a single journal entry.
 * Users can read the full content, and choose to edit or delete the entry.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EntryDetailScreen(
    entryId: String,
    navController: NavController
) {
    // Get the ViewModel using our custom factory
    val app = LocalContext.current.applicationContext as MemoJarApp
    val viewModel: EntryViewModel = viewModel(factory = app.viewModelFactory)

    // Load the entry data when this screen opens.
    // LaunchedEffect runs once when the composable first appears.
    LaunchedEffect(entryId) {
        viewModel.loadEntry(entryId)
    }

    // State variable to control whether the delete confirmation dialog is shown
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Delete confirmation dialog — appears when the user taps the delete button
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Entry") },
            text = { Text("Are you sure you want to delete this entry?") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    viewModel.deleteEntry(entryId)
                    // Navigate back to the home screen after deleting
                    navController.popBackStack(Screen.Home.route, false)
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                // Back button to return to the previous screen
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                // Edit and Delete action buttons in the top bar
                actions = {
                    // Edit button — navigates to the edit screen
                    IconButton(onClick = {
                        navController.navigate(Screen.EditEntry.createRoute(entryId))
                    }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                    }
                    // Delete button — shows the confirmation dialog
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Scrollable column for the entry content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // If the entry has an attached photo, display it at the top
            val imageUri = viewModel.imageUri.value
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Entry photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 250.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            // Entry title
            Text(
                text = viewModel.title.value,
                style = MaterialTheme.typography.headlineMedium
            )

            // Date and mood information
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Format the timestamp into a readable date string
                val dateFormat = SimpleDateFormat("MMM dd, yyyy \u2022 hh:mm a", Locale.getDefault())
                val dateString = if (viewModel.createdAt.value > 0) {
                    dateFormat.format(Date(viewModel.createdAt.value))
                } else ""

                Text(
                    text = dateString,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "\u2022",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Mood with first letter capitalized
                Text(
                    text = viewModel.mood.value.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Tags displayed as chips (if any exist)
            if (viewModel.tags.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    viewModel.tags.forEach { tag ->
                        FilterChip(
                            selected = false,
                            onClick = { },
                            label = { Text(tag) }
                        )
                    }
                }
            }

            // Full entry content
            Text(
                text = viewModel.content.value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


