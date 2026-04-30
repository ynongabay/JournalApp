# JournalApp

JournalApp is a modern Android journaling application built with Kotlin and Jetpack Compose. It lets users capture memories, thoughts, and notes in a clean, simple interface following Material Design 3 guidelines.

## Features
- **Create Entries**: Add new journal entries with a title, content, mood, and tags.
- **View Entries**: Browse all your entries in a scrollable list on the home screen.
- **Detailed View**: Tap any entry to see its full content, date, mood, and tags.
- **Edit Entries**: Update any existing entry with new content.
- **Delete Entries**: Remove entries with a delete button or from the detail screen.
- **Mood Tracking**: Select a mood emoji for each entry (awful, sad, neutral, good, happy).
- **Tags**: Add and remove tags to organize your entries.
- **Photo Attachment**: Attach photos from the device gallery using Android's Photo Picker API.
- **Local Storage**: All entries are stored locally on the device using SharedPreferences and Gson.

## Architecture
- **MVVM Pattern**: ViewModels manage UI logic, keeping it separate from the screens.
- **Repository Pattern**: A `JournalRepository` interface sits between ViewModels and the data source, making the code testable.
- **Manual Dependency Setup**: The `JournalAppApp` Application class creates shared objects (repository, ViewModel factory) that the rest of the app uses.
- **Jetpack Navigation**: Four Compose screens connected via the Navigation component.

## Screens
1. **HomeScreen** — Shows all journal entries in a list with a floating + button.
2. **EntryFormScreen** — Form for creating or editing an entry (title, content, mood, tags, photo).
3. **EntryDetailScreen** — Full view of a single entry with edit and delete options.
4. **EditEntry** — Reuses EntryFormScreen to edit an existing entry.

## Task Requirements

| Requirement | Status | Where to Find |
|---|---|---|
| Add, update, view, and delete journal entries | ✅ Met | `ui/screens/EntryFormScreen.kt` (add & edit), `ui/screens/HomeScreen.kt` (view & delete), `ui/screens/EntryDetailScreen.kt` (view & delete) |
| Overview of all journal entries | ✅ Met | `ui/screens/HomeScreen.kt` — scrollable list of entry cards |
| Detailed view of every entry | ✅ Met | `ui/screens/EntryDetailScreen.kt` — full content, date, mood, tags, photo |
| Several Android activities / screens | ✅ Met | `ui/MainActivity.kt` (single-activity architecture), four screens via `ui/navigation/JournalAppNavGraph.kt` |
| Tested using unit tests | ✅ Met | `test/.../data/JournalLocalDataSourceTest.kt` (JUnit + Robolectric), `test/.../viewmodel/JournalViewModelTest.kt` (FakeJournalRepository) |
| Material Design and app quality guidelines | ✅ Met | `ui/theme/Theme.kt` (Material 3 light/dark theme), Material 3 components used across all screens |
| Source code documentation | ✅ Met | Inline comments in every `.kt` file throughout the project |
| All code uploaded to GitHub | ✅ Met | This repository contains all source code, Gradle files, resources, tests, and manifest |

## Media
Users can attach photos to journal entries using Android's `PickVisualMedia` Photo Picker API. Selected images are displayed using the Coil image loading library, and persistent URI permissions are granted so that photos remain accessible after app restarts.

## Testing
- **Unit Tests**: The data persistence layer is tested via `JournalLocalDataSourceTest` using JUnit and Robolectric. The business logic layer is tested via `JournalViewModelTest` using a `FakeJournalRepository` for isolated testing.

## Getting Started
1. Open the project in Android Studio with JDK 17+ and SDK 34+.
2. Let Gradle sync and download dependencies.
3. Run the app on an emulator or physical device (API 26+).

## Tools and Technologies
| Tool / Library | Purpose |
|---|---|
| Kotlin | Primary language |
| Jetpack Compose | Declarative UI framework |
| Material Design 3 | Design system and components |
| Jetpack Navigation | Screen-to-screen navigation |
| Jetpack ViewModel | UI state management |
| SharedPreferences + Gson | Local data persistence |
| Coil | Image loading from URIs |
| Photo Picker API | System gallery for photo selection |
| SplashScreen API | App launch screen |
| JUnit 4 | Unit testing framework |
| Robolectric | Android framework simulation for JVM tests |
| Git & GitHub | Version control |
