package com.example.guessapp.game
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.navArgs
import com.example.guessapp.R
import com.example.guessapp.databinding.GameFragmentBinding
import com.example.guessapp.model.Player


class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel


    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        viewModel= ViewModelProvider(this)[GameViewModel::class.java]

        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }
        viewModel.score.observe(viewLifecycleOwner) { newScore ->
            binding.scoreText.text = newScore.toString()
        }
        viewModel.eventGameFinish.observe(viewLifecycleOwner){ hasFinished ->
            if(hasFinished){
                gameFinished()
                viewModel.doneNavigation()
            }
        }
        viewModel.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)

        })
        viewModel.player.observe(viewLifecycleOwner) { newPlayer ->
            binding.playerText.text = viewModel.player.value!!.name
            binding.imageView.setImageResource(viewModel.player.value!!.img)
        }


        return binding.root

    }


    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)
    }

}
