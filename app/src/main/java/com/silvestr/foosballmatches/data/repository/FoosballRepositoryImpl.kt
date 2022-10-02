package com.silvestr.foosballmatches.data.repository

import android.util.Log
import com.silvestr.foosballmatches.data.DataProvider
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.domain.FoosballRepository
import io.reactivex.Completable
import io.reactivex.Single


class FoosballRepositoryImpl() : FoosballRepository {

    override fun getGames(): Single<List<Game>> {
        return Single.just(DataProvider.games)
    }

    override fun addGame(game: Game): Completable {
        return Completable
                .fromSingle(
                        Single.just(DataProvider.games)
                                .map {
                                    DataProvider.games.add(game)
                                    DataProvider.players.add(game.player1!!)
                                    DataProvider.players.add(game.player2!!)
                                }
                )
    }

    override fun editGame(game: Game, position: Int): Completable {
        return Completable
                .fromSingle(
                        Single.just(DataProvider.games)
                                .map {
                                    game.player1.let { player1 ->
                                        val player = DataProvider.players.find { it.id == player1!!.id }
                                        DataProvider.players.remove(player)
                                        DataProvider.players.add(player1!!)
                                    }
                                    game.player2.let { player2 ->
                                        val player = DataProvider.players.find { it.id == player2!!.id }
                                        DataProvider.players.remove(player)
                                        DataProvider.players.add(player2!!)
                                    }
                                    DataProvider.games[position] = game

                                    Log.d("qqq", DataProvider.players.toString())
                                }
                )
    }

    override fun deleteGame(gameId: Int): Completable {
        return Completable
                .fromSingle(
                        Single.just(DataProvider.games)
                                .map {
                                    val game = DataProvider.games.find {
                                        gameId == it.id
                                    }
                                    DataProvider.games.remove(game)
                                }
                )
    }

    override fun getPlayers(): Single<List<Player>> {
        return Single.just(DataProvider.players.toList())
    }

}