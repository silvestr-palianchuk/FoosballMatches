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
        return Single.just(dataProvider.gamesConcurrent)
    }

    override fun addGame(game: Game): Completable {
        return Completable
            .fromSingle(
                Single.just(dataProvider.gamesConcurrent)
                    .map {
                        dataProvider.gamesConcurrent.add(game)
                        dataProvider.playersConcurrent.add(game.player1!!)
                        dataProvider.playersConcurrent.add(game.player2!!)
                    }
            )
    }

    override fun editGame(game: Game, position: Int): Completable {
        return Completable
            .fromSingle(
                Single.just(dataProvider.gamesConcurrent)
                    .map {
                        game.player1.let { player1 ->
                            val player = dataProvider.playersConcurrent.find { it.id == player1!!.id }
                            dataProvider.playersConcurrent.remove(player)
                            dataProvider.playersConcurrent.add(player1!!)
                        }
                        game.player2.let { player2 ->
                            val player = dataProvider.playersConcurrent.find { it.id == player2!!.id }
                            dataProvider.playersConcurrent.remove(player)
                            dataProvider.playersConcurrent.add(player2!!)
                        }
                        dataProvider.gamesConcurrent[position] = game
                    }
            )
    }

    override fun deleteGame(gameId: Int): Completable {
        return Completable
            .fromSingle(
                Single.just(dataProvider.gamesConcurrent)
                    .map {
                        val game = dataProvider.gamesConcurrent.find {
                            gameId == it.id
                        }
                        dataProvider.gamesConcurrent.remove(game)
                    }
            )
    }

    override fun getPlayers(): Single<Set<Player>> {
        return Single.just(dataProvider.playersConcurrent)
    }

}