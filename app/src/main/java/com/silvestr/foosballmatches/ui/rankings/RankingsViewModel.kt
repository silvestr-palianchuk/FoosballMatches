package com.silvestr.foosballmatches.ui.rankings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RankingsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Rankings Fragment"
    }
    val text: LiveData<String> = _text
}