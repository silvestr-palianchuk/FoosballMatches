package com.silvestr.foosballmatches.data.repository

import com.silvestr.foosballmatches.data.DataProvider
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.domain.FoosballRepository
import io.reactivex.Completable
import io.reactivex.Single

/*
* Instead of DataProvider we can use another data source like Data base or Retrofit service
*/

class FoosballRepositoryImpl(private val dataProvider: DataProvider) : FoosballRepository {

    override fun getGames(): Single<List<Game>> {
        return Single.just(dataProvider.games)
    }

    override fun addGame(game: Game): Completable {
        return Completable
            .fromSingle(
                Single.just(dataProvider.games)
                    .map {
                        dataProvider.games.add(game)
                        dataProvider.players.add(game.player1!!)
                        dataProvider.players.add(game.player2!!)
                    }
            )
    }

    override fun editGame(game: Game, position: Int): Completable {
        return Completable
            .fromSingle(
                Single.just(dataProvider.games)
                    .map {
                        game.player1.let { player1 ->
                            val player = dataProvider.players.find { it.id == player1!!.id }
                            dataProvider.players.remove(player)
                            dataProvider.players.add(player1!!)
                        }
                        game.player2.let { player2 ->
                            val player = dataProvider.players.find { it.id == player2!!.id }
                            dataProvider.players.remove(player)
                            dataProvider.players.add(player2!!)
                        }
                        dataProvider.games[position] = game
                    }
            )
    }

    override fun deleteGame(gameId: Int): Completable {
        return Completable
            .fromSingle(
                Single.just(dataProvider.games)
                    .map {
                        val game = dataProvider.games.find {
                            gameId == it.id
                        }
                        dataProvider.games.remove(game)
                    }
            )
    }

    override fun getPlayers(): Single<List<Player>> {
        return Single.just(dataProvider.players.toList())
    }

}