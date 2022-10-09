package com.silvestr.foosballmatches.domain

import com.silvestr.foosballmatches.data.Player
import io.reactivex.Flowable


class PlayersInteractor(private val foosballRepository: FoosballRepository) {

    fun getPlayers(): Flowable<Set<Player>> {
        return foosballRepository.getPlayers()
    }
}