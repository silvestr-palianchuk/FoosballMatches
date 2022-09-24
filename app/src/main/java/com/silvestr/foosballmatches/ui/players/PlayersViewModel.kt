package com.silvestr.foosballmatches.ui.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Players Fragment"
    }
    val text: LiveData<String> = _text
}