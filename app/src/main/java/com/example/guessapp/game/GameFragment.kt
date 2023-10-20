package com.example.guessapp.game
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.navArgs
import com.example.guessapp.R
import com.example.guessapp.databinding.GameFragmentBinding
import com.example.guessapp.model.Player


class GameFragment : Fragment() {

    private lateinit var player:Player

    private var score = 0

    private var playType = "player"

    private lateinit var playerList: MutableList<Player>

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        val gameFragmentArgs by navArgs<GameFragmentArgs>()
        playType = gameFragmentArgs.playType
        resetList(playType)
        nextPlayer()
        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        updateScoreText()
        updatePlayerText()
        return binding.root

    }


    private fun resetList(playType:String) {
        when(playType){
            "player" ->  playerList = mutableListOf(
                Player("Messi",R.drawable.messi),
                Player("Ronaldo",R.drawable.ronaldo),
                Player("Salah",R.drawable.salah),
                Player("son",R.drawable.son),
                Player("kane",R.drawable.kane),
                Player("mane",R.drawable.mane),
                Player("saka",R.drawable.saka),
                Player("foden",R.drawable.phden)
            )

        }
        playerList.shuffle()
    }


    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(score)
        findNavController(this).navigate(action)
    }

    private fun nextPlayer() {
        if (playerList.isEmpty()) {
            gameFinished()
        } else {
            player = playerList.removeAt(0)
        }
        updatePlayerText()
        updateScoreText()
    }

    private fun onSkip() {
        score--
        nextPlayer()
    }

    private fun onCorrect() {
        score++
        nextPlayer()
    }

    private fun updatePlayerText() {
        binding.playerText.text = player.name
        binding.imageView.setImageResource(player.img)
    }

    private fun updateScoreText() {
        binding.scoreText.text = score.toString()
    }
}
