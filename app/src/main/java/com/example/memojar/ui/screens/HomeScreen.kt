package com.example.memojar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.memojar.MemoJarApp
import com.example.memojar.data.JournalEntry
import com.example.memojar.ui.navigation.Screen
import com.example.memojar.viewmodel.JournalViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * HomeScreen shows a list of all journal entries.
 * It is the first screen users see when they open the app.
 * Users can tap an entry to view its details, or tap the + button to create a new one.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    // Get the app instance to access the ViewModel factory
    val app = LocalContext.current.applicationContext as MemoJarApp

    // Create (or retrieve) the JournalViewModel using our custom factory
    val viewModel: JournalViewModel = viewModel(factory = app.viewModelFactory)

    // Refresh the entry list every time this screen appears
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    // Scaffold provides the basic Material Design layout structure
    // with a top bar, content area, and floating action button
    Scaffold(
        // Top bar with the app title
        topBar = {
            TopAppBar(title = { Text("MemoJar") })
        },
        // Floating action button to create new entries
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.NewEntry.route) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Entry"
                )
            }
        }
    ) { paddingValues ->

        // Show different content depending on whether entries exist
        if (viewModel.entries.isEmpty()) {

            // Empty state — show a friendly message when there are no entries
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "No entries",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No entries yet. Tap + to create one!",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

        } else {

            // LazyColumn is like a RecyclerView — it only renders visible items,
            // making it efficient for long lists
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Create a card for each entry in the list.
                // "key" helps Compose identify each item efficiently.
                items(viewModel.entries, key = { it.id }) { entry ->
                    JournalEntryCard(
                        entry = entry,
                        onClick = {
                            navController.navigate(Screen.EntryDetail.createRoute(entry.id))
                        },
                        onDelete = {
                            viewModel.deleteEntry(entry)
                        }
                    )
                }
            }
        }
    }
}

/**
 * JournalEntryCard displays a single journal entry as a Material Design card.
 * It shows the title, a preview of the content, the mood, and the date.
 * It also includes a delete button.
 */
@Composable
fun JournalEntryCard(
    entry: JournalEntry,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    // Card is a Material Design container with rounded corners and elevation
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Top row: title on the left, delete button on the right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Entry title (takes up remaining space)
                Text(
                    text = entry.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                // Delete button (red trash icon)
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Content preview — shows the first 2 lines of the entry
            Text(
                text = entry.content,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis  // Shows "..." if text is too long
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mood badge and date
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Mood displayed as a small colored badge
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = entry.mood.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                // Date formatted as "Apr 11, 2026"
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                Text(
                    text = dateFormat.format(Date(entry.createdAt)),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
