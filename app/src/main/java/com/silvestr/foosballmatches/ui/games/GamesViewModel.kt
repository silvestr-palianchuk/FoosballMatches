package com.silvestr.foosballmatches.ui.games

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.domain.GetGamesInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GamesViewModel @Inject constructor(private val getGamesInteractor: GetGamesInteractor) :
    ViewModel() {
    val games: MutableLiveData<List<Game>> = MutableLiveData()
    private var disposable: Disposable? = null

    init {
        loadGames()
    }

    private fun loadGames() {
        disposable?.dispose()
        disposable = getGamesInteractor.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    games.value = it
                },
                {
                    Log.d("GamesViewModel", "Error: unable to load games")
                }
            )
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}