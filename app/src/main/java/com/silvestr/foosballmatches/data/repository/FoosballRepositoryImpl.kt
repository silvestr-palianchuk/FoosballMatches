package com.silvestr.foosballmatches.data.repository

import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.domain.FoosballRepository
import io.reactivex.Completable
import io.reactivex.Single


class FoosballRepositoryImpl() : FoosballRepository {

    override fun getGames(): Single<List<Game>> {
        return Single.just(games)
    }

    override fun addGame(game: Game): Completable {
        return Completable
            .fromSingle(
                Single.just(games)
                    .map {
                        games.add(game)
                        players.add(game.player1!!)
                        players.add(game.player2!!)
                    }
            )
    }

    override fun editGame(game: Game, position: Int): Completable {
        return Completable
            .fromSingle(
                Single.just(games)
                    .map {
                        game.player1.let { player1 ->
                            val player = players.find { it.id == player1!!.id }
                            players.remove(player)
                            players.add(player1!!)
                        }
                        game.player2.let { player2 ->
                            val player = players.find { it.id == player2!!.id }
                            players.remove(player)
                            players.add(player2!!)
                        }
                        games[position] = game
                    }
            )
    }

    override fun deleteGame(gameId: Int): Completable {
        return Completable
            .fromSingle(
                Single.just(games)
                    .map {
                        val game = games.find {
                            gameId == it.id
                        }
                        games.remove(game)
                    }
            )
    }

    override fun getPlayers(): Single<List<Player>> {
        return Single.just(players.toList())
    }

    private val players = mutableSetOf(
        Player(1, "Bruno", "Goncalves"),
        Player(2, "Yannick", "Correia"),
        Player(3, "Billy", "Pappas"),
        Player(4, "Tony", "Spredeman"),
        Player(5, "Todd", "Loffredo"),
        Player(6, "Frederic", "Collignon"),
        Player(7, "Brandon", "Brandon"),
        Player(8, "Tom", "Yore"),
        Player(9, "Dave ", "Gummeson"),
        Player(10, "Tracy", "McMillin")
    )

    private val games = mutableListOf(
        Game(
            1,
            System.currentTimeMillis(),
            players.find { it.id == 1 },
            players.find { it.id == 2 },
            5,
            6
        ),
        Game(
            2,
            System.currentTimeMillis(),
            players.find { it.id == 3 },
            players.find { it.id == 4 },
            7,
            8
        ),
        Game(
            3,
            System.currentTimeMillis(),
            players.find { it.id == 5 },
            players.find { it.id == 6 },
            10,
            5
        ),
        Game(
            4,
            System.currentTimeMillis(),
            players.find { it.id == 7 },
            players.find { it.id == 8 },
            3,
            8
        ),
        Game(
            5,
            System.currentTimeMillis(),
            players.find { it.id == 9 },
            players.find { it.id == 10 },
            5,
            9
        )
    )

}