package com.silvestr.foosballmatches.domain

import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.data.Ranking
import io.reactivex.Observable
import io.reactivex.Single


class RankingsInteractor(private val foosballRepository: FoosballRepository) {

    fun getRankings(): Single<List<Ranking>> {
        return foosballRepository.getPlayers()
            .toObservable()
            .flatMapIterable { it }
            .flatMap { player -> getPlayerRanking(player) }
            .filter { it.played != 0 }
            .toList()
    }

    private fun getPlayerRanking(player: Player): Observable<Ranking> {
        return foosballRepository.getGames()
            .toObservable()
            .flatMapIterable { it }
            .filter { it.player1?.id == player.id || it.player2?.id == player.id }
            .toList()
            .flatMap {
                Single.just(calculatePlayerRankings(player, it))
            }
            .toObservable()
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