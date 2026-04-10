# MemoJar

MemoJar is a modern Android journaling application built entirely with Kotlin and Jetpack Compose. Designed to be elegant and smooth, it embraces the complete Material Design 3 language to offer users a reliable, native, and engaging notebook experience.

## Features
- **Category System**: Manage entries across three distinct contexts: "Work", "Day-to-Day", and "Private". The Private category is securely protected by a 4-digit PIN lock screen!
- **Create and Edit Entries**: Log your complete thoughts using expressive local states containing dates, photos, dynamic tag filters, and mood associations.
- **Local Storage System**: Entries are securely packaged and maintained locally leveraging SharedPreferences combined with Gson serialization.
- **Swipe Gestures**: Clean swipe-to-delete behaviors utilizing strict Material 3 bounding mechanics gracefully tied into the architecture.
- **Full Searching**: A fully responsive search UI running on native Kotlin Coroutine Flows allowing users to instantly flatten query results.

## Architecture
- **MVVM Pattern**: ViewModels manage UI logic separating view rendering safely from data management.
- **Repository Pattern**: Strict boundaries are guaranteed through dependency injection mapping raw SharedPreferences/Gson wrappers behind a centralized `JournalRepository` interface.
- **Dependency Injection**: Integrated Dagger Hilt globally mapping singletons strictly via `ApplicationContext`.
- **Jetpack Navigation**: Type-safe declarative string route passing managed via standard Compose Navigation setups natively executing nested transitions!

## Testing
- **Unit Tests**: Full JVM-layer testing using `Robolectric` for parsing Android specific frameworks (`SharedPreferences`) seamlessly off real devices. Core logic uses `FakeJournalRepository` mapping Kotlin Coroutine tests securely bypassing network or disk boundaries.
- **UI Tests**: Fully loaded Hilt-backed Compose tests leveraging `@HiltAndroidTest` pushing true integration capabilities traversing native text node operations mapped against actual application states.

## Getting Started
To view and compile MemoJar:
1. Ensure you have Android Studio installed with access to JDK 17+ targeting SDK 34+.
2. Simply load up the target module folder to let Gradle finish its sync.
3. Hook up a virtual Android device (E.g. API 34+ images strongly recommended) and compile to launch!
