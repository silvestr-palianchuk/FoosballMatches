package com.silvestr.foosballmatches.ui.games

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silvestr.foosballmatches.data.DataProvider
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.data.database.DbManager
import com.silvestr.foosballmatches.domain.GamesInteractor
import com.silvestr.foosballmatches.domain.PlayersInteractor
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GamesViewModel @Inject constructor(
    private val getGamesInteractor: GamesInteractor,
    private val playersInteractor: PlayersInteractor,
    private val sharedPreferences: SharedPreferences
) :
    ViewModel() {
    val games: MutableLiveData<List<Game>> = MutableLiveData()
    val players: MutableSet<Player> = mutableSetOf()

    private var disposable: CompositeDisposable = CompositeDisposable()
    private val IS_DB_POPULATED = "IS_DB_POPULATED"

    init {
        val isDbPopulated = sharedPreferences.getBoolean(IS_DB_POPULATED, false)
        if (isDbPopulated) {
            loadGames()
            loadPlayers()
        } else {
            populateDatabase()
        }
    }

    private fun populateDatabase() {
        val addGamesToDb =
            Single.fromCallable { DbManager.db.gameDao.insertAll(DataProvider.gamesConcurrent) }
        val addPlayersToDb =
            Single.fromCallable { DbManager.db.playerDao.insertAll(DataProvider.playersConcurrent) }

        disposable.add(
            addPlayersToDb
                .flatMap {
                    addGamesToDb
                }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d("GamesViewModel", "Players and Games were added successfully")
                    loadGames()
                    loadPlayers()
                    sharedPreferences.edit().putBoolean(IS_DB_POPULATED, true).apply()
                }, {
                    Log.e("GamesViewModel", "Error: unable to add data to database -> $it")
                })
        )
    }

    private fun loadPlayers() {
        disposable.add(playersInteractor.getPlayers()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    players.addAll(it)
                    Log.d("GamesViewModel", "Players were loaded successfully")
                },
                {
                    Log.e("GamesViewModel", "Error: unable to load players -> $it")
                }
            ))

    }

    private fun loadGames() {
        disposable.add(getGamesInteractor.getGames()
            .map {
                it.sortedWith(object : Comparator<Game> {
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
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("GamesViewModel", "Games were loaded successfully")
                    games.value = it
                },
                {
                    Log.e("GamesViewModel", "Error: unable to load games -> $it")
                }
            ))
    }

    fun addGame(game: Game) {
        disposable.add(
            getGamesInteractor.addGame(game)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d("GamesViewModel", "Games was added")
                }, {
                    Log.e("GamesViewModel", "Error: unable to add game -> $it")
                })
        )
    }

    fun editGame(game: Game, position: Int) {
        disposable.add(
            getGamesInteractor.editGame(game, position)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d("GamesViewModel", "Games was edited")
                }, {
                    Log.e("GamesViewModel", "Error: unable to edit game-> $it")
                })
        )
    }

    fun deleteGame(game: Game) {
        disposable.add(
            getGamesInteractor.deleteGame(game.id)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d("GamesViewModel", "Games was deleted")
                }, {
                    Log.e("GamesViewModel", "Error: unable to delete game -> $it")
                })
        )
    }

    fun isPlayerExist(firstName: String, lastName: String): Player? {
        val player = players.filter { it.firstName == firstName && it.lastName == lastName }

        return if (player.isEmpty())
            null
        else
            player.first()
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}