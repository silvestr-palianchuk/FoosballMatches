package com.silvestr.foosballmatches.domain

import com.silvestr.foosballmatches.data.Game
import io.reactivex.Completable
import io.reactivex.Single


class GamesInteractor(private val foosballRepository: FoosballRepository) {
    fun getGames(): Single<List<Game>> {
        return foosballRepository.getGames()
    }

    fun updateGame(game: Game): Completable {
        return foosballRepository.updateGame(game)
    }

    fun deleteGame(gameId: Int): Completable {
        return foosballRepository.deleteGame(gameId)
    }
}