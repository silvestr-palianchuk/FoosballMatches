package com.silvestr.foosballmatches.domain

import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import io.reactivex.Completable
import io.reactivex.Single


interface FoosballRepository {
    fun getGames(): Single<List<Game>>
    fun updateGame(game: Game, index: Int): Completable
    fun deleteGame(gameId: Int): Completable
    fun getPlayers(): Single<List<Player>>
}