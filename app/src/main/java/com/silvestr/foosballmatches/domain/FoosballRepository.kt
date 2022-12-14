package com.silvestr.foosballmatches.domain

import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single


interface FoosballRepository {
    fun getGames(): Flowable<List<Game>>
    fun addGame(game: Game): Completable
    fun editGame(game: Game, position: Int): Completable
    fun deleteGame(gameId: Int): Completable
    fun getPlayers(): Flowable<Set<Player>>
}