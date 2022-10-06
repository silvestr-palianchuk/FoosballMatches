package com.silvestr.foosballmatches.data.repository

import android.util.Log
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.data.database.AppDatabase
import com.silvestr.foosballmatches.domain.FoosballRepository
import io.reactivex.Completable
import io.reactivex.Single

/*
* Instead of dataBase we can use another data source (for example Retrofit service)
*/

class FoosballRepositoryImpl(private val dataBase: AppDatabase) : FoosballRepository {

    override fun getGames(): Single<List<Game>> {
        return Single.fromCallable {
            val games = dataBase.gameDao.getAllGames()
            games ?: emptyList()
        }.onErrorResumeNext {
            Log.e("FoosballRepositoryImpl", "Error: unable to get games -> $it")
            Single.just(emptyList())
        }
    }

    override fun addGame(game: Game): Completable {
        return Completable
            .fromCallable {
                dataBase.playerDao.insert(game.player1!!)
                dataBase.playerDao.insert(game.player2!!)
                dataBase.gameDao.insert(game)
            }.onErrorResumeNext {
                Log.e("FoosballRepositoryImpl", "Error: unable to add game -> $it")
                Completable.complete()
            }
    }

    override fun editGame(game: Game, position: Int): Completable {
        return Completable.fromCallable {
            dataBase.gameDao.update(game)
            dataBase.playerDao.update(game.player1!!)
            dataBase.playerDao.update(game.player2!!)
            dataBase.gameDao.getAllGames()?.forEach() { gameFromDb ->
                when {
                    gameFromDb.player1?.id == game.player1.id && gameFromDb.player2?.id == game.player2.id -> {
                        val updatedGame =
                            gameFromDb.copy(player1 = game.player1, player2 = game.player2)
                        dataBase.gameDao.update(updatedGame)
                    }
                    gameFromDb.player1?.id == game.player1.id -> {
                        val updatedGame = gameFromDb.copy(player1 = game.player1)
                        dataBase.gameDao.update(updatedGame)
                    }
                    gameFromDb.player2?.id == game.player2.id -> {
                        val updatedGame = gameFromDb.copy(player2 = game.player2)
                        dataBase.gameDao.update(updatedGame)
                    }
                }
            }
        }.onErrorResumeNext {
            Log.e("FoosballRepositoryImpl", "Error: unable to update game -> $it")
            Completable.complete()
        }
    }

    override fun deleteGame(gameId: Int): Completable {
        return Completable
            .fromCallable {
                dataBase.gameDao.deleteById(gameId)
            }.onErrorResumeNext {
                Log.e("FoosballRepositoryImpl", "Error: unable to remove game -> $it")
                Completable.complete()
            }
    }

    override fun getPlayers(): Single<Set<Player>> {
        return Single.fromCallable {
            val players = dataBase.playerDao.getAllPlayers()
            players?.toSet() ?: emptySet()
        }.onErrorResumeNext {
            Log.e("FoosballRepositoryImpl", "Error: unable to get players -> $it")
            Single.just(emptySet())
        }
    }

}