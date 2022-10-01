package com.silvestr.foosballmatches.data.repository

import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.domain.FoosballRepository
import io.reactivex.Completable
import io.reactivex.Single


class FoosballRepositoryImpl() : FoosballRepository {

    override fun getGames(): Single<List<Game>> {
        return Single.just(data)
    }

    override fun addGame(game: Game): Completable {
        return Completable
            .fromSingle(
                Single.just(data)
                    .map {
                        data.add(game)
                    }
            )
    }

    override fun editGame(game: Game, position: Int): Completable {
        return Completable
            .fromSingle(
                Single.just(data)
                    .map {
                        data[position] = game
                    }
            )
    }

    override fun deleteGame(gameId: Int): Completable {
        return Completable
            .fromSingle(
                Single.just(data)
                    .map {
                        val game = data.find {
                            gameId == it.id
                        }
                        data.remove(game)
                    }
            )
    }

    override fun getPlayers(): Single<List<Player>> {
        return Single.just(listOf())
    }

    val data = mutableListOf(
        Game(
            1,
            System.currentTimeMillis(),
            Player("Bruno", "Goncalves"),
            Player("Yannick", "Correia"),
            5,
            6
        ),
        Game(
            2,
            System.currentTimeMillis(),
            Player("Billy", "Pappas"),
            Player("Tony", "Spredeman"),
            7,
            8
        ),
        Game(
            3,
            System.currentTimeMillis(),
            Player("Todd", "Loffredo"),
            Player("Frederic", "Collignon"),
            10,
            5
        ),
        Game(
            4,
            System.currentTimeMillis(),
            Player("Brandon", "Brandon"),
            Player("Tom", "Yore"),
            3,
            8
        ),
        Game(
            5,
            System.currentTimeMillis(),
            Player("Dave ", "Gummeson"),
            Player("Tracy", "McMillin"),
            5,
            9
        ),
    )
}