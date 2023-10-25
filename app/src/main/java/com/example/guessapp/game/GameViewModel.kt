package com.example.guessapp.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.guessapp.R
import com.example.guessapp.model.Player

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)
class GameViewModel :ViewModel() {
    companion object{
        private const val Done= 0L
        private const val ONE_SECOND =1000L
        private const val COUNTDOWN_TIME = 60000L
        private const val COUNTDOWN_PANIC_SECONDS = 10L

    }
    private val timer:CountDownTimer
    val _player = MutableLiveData<Player>()
    val player:LiveData<Player>
        get() = _player

    private val _score = MutableLiveData<Int>()
    val score:LiveData<Int>
        get() = _score

    private val _currentTime= MutableLiveData<Long>()
    val currentTime:LiveData<Long>
        get() = _currentTime

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish:LiveData<Boolean>
        get() = _eventGameFinish
    val currentTimeString =_currentTime.map { newTime ->
        DateUtils.formatElapsedTime(newTime)
    }
    private lateinit var playerList: MutableList<Player>

    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz
    init {
        Log.i("GameViewModel", "GameViewModel created ")
        resetList()
        _eventGameFinish.value=false
        _score.value=0
        _player.value=Player("",0)
        _currentTime.value= COUNTDOWN_TIME
        nextPlayer()

        timer=object :CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            @Override
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value= (millisUntilFinished / ONE_SECOND)
                if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS) {
                    _eventBuzz.value = BuzzType.COUNTDOWN_PANIC
                }
            }

            @Override
            override fun onFinish() {
                _currentTime.value = Done
                _eventGameFinish.value = true
                _eventBuzz.value = BuzzType.GAME_OVER
            }
        }
        timer.start()

    }

    private fun resetList() {
              playerList = mutableListOf(
                Player("Lionel Messi", R.drawable.messi),
                Player("Cristiano Ronaldo", R.drawable.ronaldo),
                Player("Mohamed Salah", R.drawable.salah),
                Player("Son", R.drawable.son),
                Player("Harry Kane", R.drawable.kane),
                Player("Bukayo Saka", R.drawable.saka),
                Player("Erling Haaland", R.drawable.haaland),
                Player("Kylian Mbappé", R.drawable.kylian),
                Player("Jude Bellingham", R.drawable.bellingham),
                Player("Vinicius Junior", R.drawable.vinicius),
                Player("Jamal Musiala", R.drawable.jamal),
                Player("Gavi", R.drawable.gavi),
                Player("Rodrygo", R.drawable.rodrygo),
                Player("Luka Modrić", R.drawable.luka),
                  Player("Phil Foden", R.drawable.phden)
                  //Player("Eduardo Camavinga", R.drawable.camavinga),
//                Player("Mane", R.drawable.mane),
                  //  Player("Frenkie De Young", R.drawable.saka),
//                Player("Neymar", R.drawable.saka),
//                Player("Pedri", R.drawable.saka),
//                Player("Marcus Rashford", R.drawable.saka),
//                Player("Declan Rice", R.drawable.saka),
//                Player("Federico Valverde", R.drawable.saka),
//                Player("Karim Benzema", R.drawable.saka),
//                Player("Aurélien Tchouaméni", R.drawable.saka),
//                Player("Joselu", R.drawable.saka),
//                Player("kepa arrizabalaga", R.drawable.saka),
//                Player("Kai Havertz", R.drawable.saka),
//                Player("André Onana", R.drawable.saka),
//                Player("Thiago Silva", R.drawable.saka),
            )
        playerList.shuffle()
    }

    private fun nextPlayer() {
        if (playerList.isEmpty()) {
           // _eventGameFinish.value=true

            resetList()
        }
        //else {
            _player.value = playerList.removeAt(0)
        //}

    }
     fun onSkip() {
        _score.value=(score.value)?.minus(1)
        nextPlayer()
    }

     fun onCorrect() {
        _score.value = (score.value)?.plus(1)
         _eventBuzz.value = BuzzType.CORRECT

         nextPlayer()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("GameViewModel", "GameViewModel onCleared: ")
    }
    fun doneNavigation(){
        _eventGameFinish.value=false
    }
    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }
}
