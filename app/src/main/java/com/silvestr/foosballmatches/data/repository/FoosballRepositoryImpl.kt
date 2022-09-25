package com.silvestr.foosballmatches.data.repository

import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.domain.FoosballRepository
import io.reactivex.Single


class FoosballRepositoryImpl() : FoosballRepository {

    override fun getGames(): Single<List<Game>> {
        return Single.just(generateGameData())
    }

    override fun getPlayers(): Single<List<Player>> {
        return Single.just(listOf())
    }

    private fun generateGameData(): List<Game>{
        return listOf(
            Game(System.currentTimeMillis(), Player("Bruno", "Goncalves"), Player("Yannick", "Correia"),5,6),
            Game(System.currentTimeMillis(), Player("Billy", "Pappas"), Player("Tony", "Spredeman"),7,8),
            Game(System.currentTimeMillis(), Player("Todd", "Loffredo"), Player("Frederic", "Collignon"),10,5),
            Game(System.currentTimeMillis(), Player("Brandon", "Brandon"), Player("Tom", "Yore"),3,8),
            Game(System.currentTimeMillis(), Player("Dave ", "Gummeson"), Player("Tracy", "McMillin"),5,9),
        )
    }
}