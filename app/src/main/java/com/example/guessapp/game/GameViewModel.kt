package com.example.guessapp.game

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guessapp.R
import com.example.guessapp.model.Player

class GameViewModel :ViewModel() {
    var player = MutableLiveData<Player>()

    var score = MutableLiveData<Int>()

    var playType = "player"

    private lateinit var playerList: MutableList<Player>

    init {
        Log.i("GameViewModel", "GameViewModel created ")
        resetList(playType)

        score.value=0
        player.value=Player("",0)
        nextPlayer()
    }

    private fun resetList(playType:String) {
        when(playType){
            "player" ->  playerList = mutableListOf(
                Player("Messi", R.drawable.messi),
                Player("Ronaldo", R.drawable.ronaldo),
                Player("Salah", R.drawable.salah),
                Player("son", R.drawable.son),
                Player("kane", R.drawable.kane),
                Player("mane", R.drawable.mane),
                Player("saka", R.drawable.saka),
                Player("foden", R.drawable.phden)
            )

        }
        playerList.shuffle()
    }

    private fun nextPlayer() {
        if (playerList.isEmpty()) {
          //  gameFinished()
        } else {
            player.value = playerList.removeAt(0)
        }

    }
     fun onSkip() {
        score.value=(score.value)?.minus(1)
        nextPlayer()
    }

     fun onCorrect() {
        score.value = (score.value)?.plus(1)
        nextPlayer()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel onCleared: ")
    }
}