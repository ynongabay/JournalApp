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

// Shows the full details of one entry with edit and delete options
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EntryDetailScreen(entryId: String, navController: NavController) {

    val app = LocalContext.current.applicationContext as MemoJarApp
    val viewModel: EntryViewModel = viewModel(factory = app.viewModelFactory)

    LaunchedEffect(entryId) { viewModel.loadEntry(entryId) }

    var showDeleteDialog by remember { mutableStateOf(false) }

    // Delete confirmation popup
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Entry") },
            text = { Text("Are you sure you want to delete this entry?") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    viewModel.deleteEntry(entryId)
                    navController.popBackStack(Screen.Home.route, false)
                }) { Text("Delete", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.EditEntry.createRoute(entryId))
                    }) { Icon(Icons.Default.Edit, contentDescription = "Edit") }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
                .verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Show attached photo if there is one
            val imageUri = viewModel.imageUri.value
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Entry photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().heightIn(max = 250.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            // Title
            Text(viewModel.title.value, style = MaterialTheme.typography.headlineMedium)

            // Date and mood
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val dateFormat = SimpleDateFormat("MMM dd, yyyy \u2022 hh:mm a", Locale.getDefault())
                val dateString = if (viewModel.createdAt.value > 0)
                    dateFormat.format(Date(viewModel.createdAt.value)) else ""
                Text(dateString, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("\u2022", style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(viewModel.mood.value.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            // Tags
            if (viewModel.tags.isNotEmpty()) {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    viewModel.tags.forEach { tag ->
                        FilterChip(selected = false, onClick = { }, label = { Text(tag) })
                    }
                }
            }

            // Full content
            Text(viewModel.content.value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

