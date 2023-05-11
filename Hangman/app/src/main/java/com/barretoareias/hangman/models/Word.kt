package com.barretoareias.hangman.models

class Word(val value : String, val category: WordCategory) {
}

enum class WordCategory {
    ANIMALS, COLORS, FOOD, GAMES, SPORTS, EMOTIONS
}