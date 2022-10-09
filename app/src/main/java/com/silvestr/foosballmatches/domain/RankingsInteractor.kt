package com.silvestr.foosballmatches.domain

import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.data.Ranking
import io.reactivex.Flowable


class RankingsInteractor(private val foosballRepository: FoosballRepository) {

    fun getRankings(): Flowable<List<Ranking>> {
        return Flowable.zip(
            foosballRepository.getPlayers(),
            foosballRepository.getGames()
        ) { playersSet, gamesList ->
            val rankings = mutableListOf<Ranking>()
            playersSet.forEach {
                rankings.add(getPlayerRanking(it, gamesList))
            }
            return@zip rankings.filter { it.played != 0 }
        }
    }

    private fun getPlayerRanking(player: Player, gamesList: List<Game>): Ranking {
        val games = gamesList.filter { it.player1?.id == player.id || it.player2?.id == player.id }
        return calculatePlayerRankings(player, games)
    }

    private fun calculatePlayerRankings(player: Player, games: List<Game>): Ranking {
        val played = games.size
        var won = 0

        games.forEach {
            if ((it.player1?.id == player.id && it.score1 > it.score2) ||
                (it.player2?.id == player.id && it.score2 > it.score1)
            ) {
                won++
            }
        }

        return Ranking(
            player = player,
            played = played,
            won = won
        )
    }
}