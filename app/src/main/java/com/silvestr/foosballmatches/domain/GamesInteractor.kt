package com.silvestr.foosballmatches.domain

import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.database.DbManager
import io.reactivex.Completable
import io.reactivex.Single


class GamesInteractor(private val foosballRepository: FoosballRepository) {
    fun getGames(): Single<List<Game>> {
        return foosballRepository.getGames()
    }

    fun editGame(game: Game, position: Int): Completable {
        return foosballRepository.editGame(game, position)
    }

    fun deleteGame(gameId: Int): Completable {
        return foosballRepository.deleteGame(gameId)
    }

    fun addGame(game: Game): Completable {
        return foosballRepository.addGame(game)
    }
}