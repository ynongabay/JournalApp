package com.example.memojar.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.memojar.MemoJarApp
import com.example.memojar.viewmodel.EntryViewModel

// Form screen for creating a new entry or editing an existing one
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryFormScreen(entryId: String?, navController: NavController) {

    val app = LocalContext.current.applicationContext as MemoJarApp
    val viewModel: EntryViewModel = viewModel(factory = app.viewModelFactory)
    val context = LocalContext.current

    // Photo picker — opens gallery and saves the selected image
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            // Keep permission so photo stays after app restart
            context.contentResolver.takePersistableUriPermission(
                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.imageUri.value = uri.toString()
        }
    }

    // If editing, load the entry data
    LaunchedEffect(entryId) {
        if (entryId != null) viewModel.loadEntry(entryId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (entryId == null) "New entry" else "Edit entry") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        viewModel.saveEntry()
                        navController.popBackStack()
                    }) { Text("Save") }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
                .verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title field
            OutlinedTextField(
                value = viewModel.title.value,
                onValueChange = { viewModel.title.value = it },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Content field
            OutlinedTextField(
                value = viewModel.content.value,
                onValueChange = { viewModel.content.value = it },
                label = { Text("Content") },
                minLines = 5,
                modifier = Modifier.fillMaxWidth()
            )

            // Mood picker
            MoodSelector(
                selectedMood = viewModel.mood.value,
                onMoodSelected = { viewModel.mood.value = it }
            )

            // Tag input
            TagInput(
                tags = viewModel.tags,
                onAddTag = { viewModel.tags.add(it) },
                onRemoveTag = { viewModel.tags.remove(it) }
            )

            // Add Photo button
            OutlinedButton(
                onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.AddAPhoto, contentDescription = "Add Photo",
                    modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Photo")
            }

            // Show photo preview if one is selected
            val currentImageUri = viewModel.imageUri.value
            if (currentImageUri != null) {
                Box(modifier = Modifier.fillMaxWidth().heightIn(max = 200.dp)) {
                    AsyncImage(
                        model = currentImageUri,
                        contentDescription = "Attached photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
                    )
                    // Remove photo button
                    FilledTonalIconButton(
                        onClick = { viewModel.imageUri.value = null },
                        modifier = Modifier.align(Alignment.TopEnd).padding(4.dp).size(28.dp)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Remove photo",
                            modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

// Row of mood icons — selected one is highlighted
@Composable
fun MoodSelector(selectedMood: String, onMoodSelected: (String) -> Unit) {
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
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            moods.forEach { (moodName, icon) ->
                if (selectedMood == moodName) {
                    FilledTonalIconButton(onClick = { onMoodSelected(moodName) }) {
                        Icon(imageVector = icon, contentDescription = moodName)
                    }
                } else {
                    IconButton(onClick = { onMoodSelected(moodName) }) {
                        Icon(imageVector = icon, contentDescription = moodName)
                    }
                }
            }
        }
    }
}

// Text field to add tags, shown as removable chips
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TagInput(tags: List<String>, onAddTag: (String) -> Unit, onRemoveTag: (String) -> Unit) {
    var tagText by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = tagText,
            onValueChange = { tagText = it },
            label = { Text("Add Tag") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (tagText.isNotBlank() && !tags.contains(tagText.trim())) {
                    onAddTag(tagText.trim())
                }
                tagText = ""
            }),
            singleLine = true
        )

        if (tags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)) {
                tags.forEach { tag ->
                    InputChip(
                        selected = false, onClick = { }, label = { Text(tag) },
                        trailingIcon = {
                            Icon(Icons.Default.Close, contentDescription = "Remove tag",
                                modifier = Modifier.size(InputChipDefaults.IconSize)
                                    .clickable { onRemoveTag(tag) })
                        }
                    )
                }
            }
        }
    }
}

