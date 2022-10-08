package com.silvestr.foosballmatches.domain

import com.silvestr.foosballmatches.data.Player
import io.reactivex.Observable
import io.reactivex.Single


class PlayersInteractor(private val foosballRepository: FoosballRepository) {

    fun getPlayers(): Observable<Set<Player>> {
        return Observable.zip(
            foosballRepository.getPlayers(),
            foosballRepository.getGames()
        ) { playersSet, gamesList ->
            /*
            * Filtering players who did not participate in games
            */
            playersSet.filter { player ->
                gamesList.any { game ->
                    game.player1?.id == player.id || game.player2?.id == player.id
                }
            }.toSet()
        }
    }
}