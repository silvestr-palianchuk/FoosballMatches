package com.silvestr.foosballmatches.domain

import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import io.reactivex.Single


interface FoosballRepository {
    fun getGames(): Single<List<Game>>
    fun getPlayers(): Single<List<Player>>
}