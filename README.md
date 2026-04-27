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
- **Local Storage**: All entries are stored locally on the device using SharedPreferences and Gson.

## Architecture
- **MVVM Pattern**: ViewModels manage UI logic, keeping it separate from the screens.
- **Repository Pattern**: A `JournalRepository` interface sits between ViewModels and the data source, making the code testable.
- **Manual Dependency Setup**: The `JournalAppApp` Application class creates shared objects (repository, ViewModel factory) that the rest of the app uses.
- **Jetpack Navigation**: Four Compose screens connected via the Navigation component.

## Screens
1. **HomeScreen** — Shows all journal entries in a list with a floating + button.
2. **EntryFormScreen** — Form for creating or editing an entry (title, content, mood, tags).
3. **EntryDetailScreen** — Full view of a single entry with edit and delete options.
4. **EditEntry** — Reuses EntryFormScreen to edit an existing entry.

## Testing
- **Unit Tests**: The application is tested using unit tests. The data persistence layer is tested via `JournalLocalDataSourceTest` using JUnit and Robolectric. The business logic layer is tested via `JournalViewModelTest` using a `FakeJournalRepository` for isolated testing.

## Getting Started
1. Open the project in Android Studio with JDK 17+ and SDK 34+.
2. Let Gradle sync and download dependencies.
3. Run the app on an emulator or physical device (API 26+).

