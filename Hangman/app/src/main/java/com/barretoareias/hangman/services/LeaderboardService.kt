package com.barretoareias.hangman.services

import android.app.Person
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.barretoareias.hangman.models.Player
import com.barretoareias.hangman.models.Word
import com.barretoareias.hangman.models.WordCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LeaderboardService @Inject constructor(private val storageService: StorageService) : Service() {

    private val LEADERBOARD_KEY = "LEADERBOARD"
    private var players : MutableList<Player>

    init {
        val players = storageService.read<MutableList<Player>>(LEADERBOARD_KEY) ?: emptyList<Player>().toMutableList()
        this.players = players
    }

    fun getLeaderboard() : List<String> {
        return players.sortedByDescending { it.score }.mapIndexed { i, p -> ""+(i+1)+": " + p.toString() }
    }

    fun savePlayerScore(player : Player){
        players.add(player)
        storageService.write(LEADERBOARD_KEY, players)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}