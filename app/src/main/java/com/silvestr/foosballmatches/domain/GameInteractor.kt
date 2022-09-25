package com.silvestr.foosballmatches.domain

import com.silvestr.foosballmatches.data.Game
import io.reactivex.Single
import javax.inject.Inject


class GetGamesInteractor (private val foosballRepository: FoosballRepository) {
    fun execute(): Single<List<Game>> {
        return foosballRepository.getGames()
    }
}