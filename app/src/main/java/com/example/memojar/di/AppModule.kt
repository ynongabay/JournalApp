package com.example.memojar.di

import android.content.Context
import com.example.memojar.data.JournalLocalDataSource
import com.example.memojar.data.JournalRepository
import com.example.memojar.data.JournalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJournalLocalDataSource(
        @ApplicationContext context: Context
    ): JournalLocalDataSource {
        return JournalLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideJournalRepository(
        localDataSource: JournalLocalDataSource
    ): JournalRepository {
        return JournalRepositoryImpl(localDataSource)
    }
}
