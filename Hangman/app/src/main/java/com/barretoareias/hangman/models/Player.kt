package com.barretoareias.hangman.models

 class Player(var name: String,var score: Int) {

    override fun toString(): String {
        return "$score - $name"
    }
}