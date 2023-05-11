package com.barretoareias.hangman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.barretoareias.hangman.databinding.ActivityGameBinding
import com.barretoareias.hangman.databinding.ActivityMainBinding
import com.barretoareias.hangman.services.LeaderboardService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var leaderboardService: LeaderboardService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(leaderboardService.getLeaderboard().isEmpty()) {
            binding.btnLeaderboard.isEnabled = false
        }
    }

    fun onPlay(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun onLeaderboard(view: View) {
        val intent = Intent(this, LeaderboardActivity::class.java)
        startActivity(intent)
    }
}