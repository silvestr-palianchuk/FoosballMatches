package com.silvestr.foosballmatches.ui.games

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.domain.GamesInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GamesViewModel @Inject constructor(private val getGamesInteractor: GamesInteractor) :
    ViewModel() {
    val games: MutableLiveData<List<Game>> = MutableLiveData()
    private var disposable: CompositeDisposable = CompositeDisposable()

    init {
        loadGames()
    }

    private fun loadGames() {
        disposable.add(getGamesInteractor.getGames()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    games.value = it.sortedWith(object : Comparator<Game> {
                        override fun compare(p0: Game, p1: Game): Int {
                            if (p0.date > p1.date) {
                                return -1
                            }
                            if (p0.date == p1.date) {
                                return 0
                            }
                            return 1
                        }
                    })
                },
                {
                    Log.d("GamesViewModel", "Error: unable to load games")
                }
            ))
    }

    fun addGame(game: Game) {
        disposable.add(
            getGamesInteractor.addGame(game)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loadGames()
                }, {
                    Log.d("GamesViewModel", "Error: unable to add game")
                })
        )
    }

    fun editGame(game: Game, position: Int) {
        disposable.add(
            getGamesInteractor.editGame(game, position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loadGames()
                }, {
                    Log.d("GamesViewModel", "Error: unable to edit game")
                })
        )
    }

    fun deleteGame(game: Game) {
        disposable.add(
            getGamesInteractor.deleteGame(game.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loadGames()
                }, {
                    Log.d("GamesViewModel", "Error: unable to delete game")
                })
        )
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}