package com.example.guessapp.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guessapp.R
import com.example.guessapp.model.Player

class GameViewModel :ViewModel() {
    val _player = MutableLiveData<Player>()
    val player:LiveData<Player>
        get() = _player

    private val _score = MutableLiveData<Int>()
    val score:LiveData<Int>
        get() = _score

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish:LiveData<Boolean>
        get() = _eventGameFinish



    private lateinit var playerList: MutableList<Player>

    init {
        Log.i("GameViewModel", "GameViewModel created ")
        resetList()
        _eventGameFinish.value=false
        _score.value=0
        _player.value=Player("",0)
        nextPlayer()
    }

    private fun resetList() {
              playerList = mutableListOf(
                Player("Messi", R.drawable.messi),
                Player("Ronaldo", R.drawable.ronaldo),
                Player("Salah", R.drawable.salah),
                Player("son", R.drawable.son),
                Player("kane", R.drawable.kane),
                Player("mane", R.drawable.mane),
                Player("saka", R.drawable.saka),
                Player("foden", R.drawable.phden)
            )
        playerList.shuffle()
    }

    private fun nextPlayer() {
        if (playerList.isEmpty()) {
          //  gameFinished()
            _eventGameFinish.value=true
        } else {
            _player.value = playerList.removeAt(0)
        }

    }
     fun onSkip() {
        _score.value=(score.value)?.minus(1)
        nextPlayer()
    }

     fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextPlayer()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel onCleared: ")
    }
    fun doneNavigation(){
        _eventGameFinish.value=false
    }
}