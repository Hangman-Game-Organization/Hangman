package com.barretoareias.hangman.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.barretoareias.hangman.GameActivity
import com.barretoareias.hangman.models.Word
import com.barretoareias.hangman.models.WordCategory
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordService @Inject constructor(@ApplicationContext val context: Context, private val storageService: StorageService) : Service() {

    private val WORDS_KEY = "WORDS"
    lateinit var words : List<Word>

    init {
        words = Gson().fromJson(getJson(), Array<Word>::class.java).toList()
    }

    private fun getJson() : String {
        return context.assets.open("words.json").bufferedReader().use { it.readText() }
    }

    fun getRandomWord() : Word {
        return words.random()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}