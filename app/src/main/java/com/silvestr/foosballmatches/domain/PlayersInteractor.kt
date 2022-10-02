package com.silvestr.foosballmatches.domain

import com.silvestr.foosballmatches.data.Player
import io.reactivex.Single


class PlayersInteractor(private val foosballRepository: FoosballRepository) {

    fun getPlayers(): Single<List<Player>> {
        return foosballRepository.getPlayers()
    }

}