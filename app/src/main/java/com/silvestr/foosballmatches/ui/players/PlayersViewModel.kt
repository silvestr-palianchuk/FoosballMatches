package com.silvestr.foosballmatches.ui.players

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.domain.GamesInteractor
import com.silvestr.foosballmatches.domain.PlayersInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PlayersViewModel @Inject constructor(private val getPlayersInteractor: PlayersInteractor) :
    ViewModel() {

    val players: MutableLiveData<Set<Player>> = MutableLiveData()
    private var disposable: CompositeDisposable = CompositeDisposable()

    init {
        loadPlayers()
    }

    fun loadPlayers() {
        disposable.add(getPlayersInteractor.getPlayers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    players.value = it
                },
                {
                    Log.d("PlayersViewModel", "Error: unable to load players")
                }
            ))

    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}