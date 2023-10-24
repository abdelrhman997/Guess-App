package com.example.guessapp.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.guessapp.R
import com.example.guessapp.databinding.ScoreFragmentBinding


class ScoreFragment : Fragment() {
    lateinit var scoreViewModel: ScoreViewModel
    lateinit var scoreViewModelFactory: ScoreViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.score_fragment, container, false)
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        scoreViewModelFactory= ScoreViewModelFactory(scoreFragmentArgs.score)

        scoreViewModel=ViewModelProvider(this,scoreViewModelFactory).get(ScoreViewModel::class.java)

//        binding.scoreText.text = scoreFragmentArgs.score.toString()
        binding.scoreText.text = scoreViewModel.finalScore.value.toString()
        binding.playAgainButton.setOnClickListener { onPlayAgain() }

        return binding.root
    }

    private fun onPlayAgain() {
        findNavController().navigate(ScoreFragmentDirections.actionRestart())
    }
}
