package com.barretoareias.hangman

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.allViews
import com.barretoareias.hangman.databinding.ActivityGameBinding
import com.barretoareias.hangman.models.Player
import com.barretoareias.hangman.models.Word
import com.barretoareias.hangman.services.LeaderboardService
import com.barretoareias.hangman.services.WordService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GameActivity : AppCompatActivity() {

    private var tries = 0;
    private var points = 0;
    private lateinit var player : Player
    private lateinit var binding: com.barretoareias.hangman.databinding.ActivityGameBinding
    private lateinit var word : Word
    private val letterViews = mutableListOf<TextView>()
    @Inject lateinit var wordService: WordService
    @Inject lateinit var leaderboardService : LeaderboardService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NameFragment {
            this.player = Player(it,0)
        }.show(supportFragmentManager, NameFragment.TAG)

        startGame()
    }

    private fun startGame() {
        tries = 5
        loadWordView()
        updateStatus()
    }

    private fun loadWordView() {
        word = wordService.getRandomWord()

        binding.txtTheme.text = word.category.toString()

        val vi = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding.letters.removeAllViews()
        word.value.forEach {
            val view = vi.inflate(R.layout.letter, null);
            val letterTxt = view.findViewById<TextView>(R.id.txtLetter);
            letterViews.add(letterTxt)
            letterTxt.text = it.toString();
            letterTxt.visibility = View.INVISIBLE
            binding.letters.addView(view)
        }
    }

    fun onLetterChosen(view: View) {
        val btn = view as Button
        btn.isEnabled = false
        val letters = tryToRevelLetter(btn.text.toString())
        if(letters > 0)
            points += letters
        else
            tries--
        updateStatus()
    }

    private fun tryToRevelLetter(letter : String) : Int {
        var letters = 0
        letterViews.forEach {
            if (it.text.toString() == letter) {
                letters++
                it.visibility = View.VISIBLE
            }
        }
        return letters
    }

    private fun updateStatus() {
        val hasWon = letterViews.all { it.visibility == View.VISIBLE }
        if(tries <= 0)
            gameOver()
        else if (hasWon)
            gameWon()
        binding.txtTries.text = getString(R.string.txtTries,tries)
        binding.txtPoints.text = getString(R.string.txtPoints,points)
    }

    private fun gameOver() {
        player.score = points
        leaderboardService.savePlayerScore(player)
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle(R.string.game_over_title)
        alertBuilder.setCancelable(false)
        alertBuilder.setMessage(R.string.game_over_message)
        alertBuilder.setPositiveButton("Play again") { _, _ ->
            points = 0
            clearScreen()
            startGame()
        }
        alertBuilder.setNegativeButton("Leaderboard") { _, _ ->
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
        alertBuilder.create().show()
    }

    private fun gameWon(){
        points += letterViews.count()
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle(R.string.game_won_title)
        alertBuilder.setCancelable(false)
        alertBuilder.setMessage(R.string.game_won_message)
        alertBuilder.setPositiveButton("Keep playing") { _, _ ->
            clearScreen()
            startGame()
        }
        alertBuilder.create().show()
    }

    private fun clearScreen(){
        letterViews.clear()
        binding.letters.removeAllViews()
        binding.keyboardFirstRow.allViews.forEach { it.isEnabled = true }
        binding.keyboardSecondRow.allViews.forEach { it.isEnabled = true }
        binding.keyboardThirdRow.allViews.forEach { it.isEnabled = true }
    }

}