package com.example.memojar.ui.screens

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.memojar.viewmodel.EntryViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memojar.viewmodel.JournalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryFormScreen(
    entryId: String?,
    navController: NavController,
    viewModel: EntryViewModel = hiltViewModel(),
    journalViewModel: JournalViewModel = hiltViewModel()
) {
    val currentCategory by journalViewModel.selectedCategory.collectAsStateWithLifecycle()

    LaunchedEffect(entryId) {
        if (entryId != null) {
            viewModel.loadEntry(entryId)
        } else {
            viewModel.category.value = currentCategory
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (entryId == null) "New entry" else "Edit entry") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val displayCategory = when (viewModel.category.value) {
                "work" -> "Work"
                "day_to_day" -> "Day-to-Day"
                "private" -> "Private"
                else -> viewModel.category.value
            }

            OutlinedTextField(
                value = displayCategory,
                onValueChange = {},
                label = { Text("Category") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.title.value,
                onValueChange = { viewModel.title.value = it },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.content.value,
                onValueChange = { viewModel.content.value = it },
                label = { Text("Content") },
                minLines = 5,
                modifier = Modifier.fillMaxWidth()
            )

            MoodSelector(
                selectedMood = viewModel.mood.value,
                onMoodSelected = { viewModel.mood.value = it }
            )

            TagInput(
                tags = viewModel.tags,
                onAddTag = { viewModel.tags.add(it) },
                onRemoveTag = { viewModel.tags.remove(it) }
            )

            ImagePickerField(
                imagePath = viewModel.imagePath.value,
                onImagePicked = { viewModel.imagePath.value = it?.toString() }
            )
        }
    }
}

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
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

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TagInput(
    tags: List<String>,
    onAddTag: (String) -> Unit,
    onRemoveTag: (String) -> Unit
) {
    var tagText by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = tagText,
            onValueChange = { tagText = it },
            label = { Text("Add Tag") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (tagText.isNotBlank() && !tags.contains(tagText.trim())) {
                        onAddTag(tagText.trim())
                    }
                    tagText = ""
                }
            ),
            singleLine = true
        )

        if (tags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                tags.forEach { tag ->
                    InputChip(
                        selected = false,
                        onClick = { },
                        label = { Text(tag) },
                        modifier = Modifier.defaultMinSize(minHeight = 48.dp, minWidth = 48.dp),
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

@Composable
fun ImagePickerField(
    imagePath: String?,
    onImagePicked: (Uri?) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? -> onImagePicked(uri) }

    Column {
        OutlinedButton(
            onClick = {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (imagePath == null) "Pick Image" else "Change Image")
        }

        if (imagePath != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imagePath,
                    contentDescription = "Selected image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}
