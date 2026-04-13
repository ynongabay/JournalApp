package com.example.memojar

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.memojar.data.JournalLocalDataSource
import com.example.memojar.data.JournalRepository
import com.example.memojar.data.JournalRepositoryImpl
import com.example.memojar.viewmodel.EntryViewModel
import com.example.memojar.viewmodel.JournalViewModel

// This runs when the app starts. It creates shared objects.
class MemoJarApp : Application() {

    lateinit var repository: JournalRepository

    override fun onCreate() {
        super.onCreate()
        val dataSource = JournalLocalDataSource(this)
        repository = JournalRepositoryImpl(dataSource)
    }

    // Factory tells Android how to create our ViewModels with the repository
    val viewModelFactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(JournalViewModel::class.java) ->
                    JournalViewModel(repository) as T
                modelClass.isAssignableFrom(EntryViewModel::class.java) ->
                    EntryViewModel(repository) as T
                else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
            }
        }
    }
}

