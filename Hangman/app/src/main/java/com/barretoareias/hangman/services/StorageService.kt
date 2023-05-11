package com.barretoareias.hangman.services

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageService @Inject constructor(val context: Context,val gson: Gson) {
    val SHARED_PREFERANCES  = "HangmanSharedPreferences"

    inline fun <reified T> read(key: String): T? {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERANCES, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(key, null)
        return gson.fromJson(json, object : TypeToken<T>() {}.type)
    }

    fun <T> write(key: String, value: T) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERANCES, Context.MODE_PRIVATE)
        val json = gson.toJson(value)
        sharedPreferences.edit().putString(key, json).apply()
    }
}