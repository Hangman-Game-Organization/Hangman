package com.barretoareias.hangman.services

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {
    @Provides
    @Singleton
    fun provideWordService(@ApplicationContext context: Context, storageService: StorageService) : WordService {
        return WordService(context, storageService)
    }

    @Provides
    @Singleton
    fun provideLeaderboardService(storageService: StorageService) : LeaderboardService {
        return LeaderboardService(storageService)
    }

    @Provides
    @Singleton
    fun provideStorageService(@ApplicationContext context: Context) : StorageService {
        return StorageService(context, Gson())
    }


}