package com.silvestr.foosballmatches.domain

import com.silvestr.foosballmatches.data.Player
import io.reactivex.Single


class PlayersInteractor(private val foosballRepository: FoosballRepository) {

    fun getPlayers(): Single<List<Player>> {
        return Single.zip(
                foosballRepository.getPlayers(),
                foosballRepository.getGames()
        ) { playersList, gamesList ->
            playersList.filter { player ->
                gamesList.any { game ->
                    game.player1?.id == player.id || game.player2?.id == player.id
                }
            }
        }
    }
}