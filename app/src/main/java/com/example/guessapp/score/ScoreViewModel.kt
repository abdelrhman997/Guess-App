package com.example.guessapp.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore:Int) : ViewModel() {

    private val _finalScore=MutableLiveData<Int>()
    val finalScore:LiveData<Int>
        get() = _finalScore

    init {
        Log.i("ScoreViewModel", ":$finalScore ")
        _finalScore.value=finalScore
    }
}