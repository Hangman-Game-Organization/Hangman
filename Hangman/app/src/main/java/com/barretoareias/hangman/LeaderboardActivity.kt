package com.barretoareias.hangman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.barretoareias.hangman.databinding.ActivityGameBinding
import com.barretoareias.hangman.databinding.ActivityLeaderboardBinding
import com.barretoareias.hangman.services.LeaderboardService
import com.barretoareias.hangman.services.WordService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LeaderboardActivity : AppCompatActivity() {

    private lateinit var binding: com.barretoareias.hangman.databinding.ActivityLeaderboardBinding
    @Inject lateinit var leaderboardService: LeaderboardService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val values = leaderboardService.getLeaderboard()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, values)
        binding.leaderList.adapter = adapter
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}