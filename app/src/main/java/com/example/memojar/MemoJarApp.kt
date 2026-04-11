package com.example.memojar

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.memojar.data.JournalLocalDataSource
import com.example.memojar.data.JournalRepository
import com.example.memojar.data.JournalRepositoryImpl
import com.example.memojar.viewmodel.EntryViewModel
import com.example.memojar.viewmodel.JournalViewModel

/**
 * MemoJarApp is the custom Application class for MemoJar.
 * It is the very first thing that runs when the app starts.
 *
 * We use it to create shared objects (like the repository) that
 * the rest of the app needs. This is a simple way to share
 * dependencies without using complex frameworks.
 */
class MemoJarApp : Application() {

    // The repository that all ViewModels will use to access data.
    // "lateinit" means we will initialize it later (in onCreate).
    lateinit var repository: JournalRepository

    override fun onCreate() {
        super.onCreate()

        // Create the data source (handles reading/writing to SharedPreferences)
        val dataSource = JournalLocalDataSource(this)

        // Create the repository (wraps the data source)
        repository = JournalRepositoryImpl(dataSource)
    }

    /**
     * A factory that creates ViewModels with the repository.
     *
     * Normally, Android creates ViewModels using a no-argument constructor.
     * Since our ViewModels need a repository parameter, we use this factory
     * to tell Android HOW to create them.
     */
    val viewModelFactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when {
                // If a JournalViewModel is requested, create one with the repository
                modelClass.isAssignableFrom(JournalViewModel::class.java) ->
                    JournalViewModel(repository) as T

                // If an EntryViewModel is requested, create one with the repository
                modelClass.isAssignableFrom(EntryViewModel::class.java) ->
                    EntryViewModel(repository) as T

                // If an unknown ViewModel is requested, throw an error
                else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
            }
        }
    }
}
