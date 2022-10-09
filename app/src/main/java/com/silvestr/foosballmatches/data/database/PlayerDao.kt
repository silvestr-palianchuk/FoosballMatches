package com.silvestr.foosballmatches.data.database

import androidx.room.*
import com.silvestr.foosballmatches.data.Player
import io.reactivex.Flowable

@Dao
interface PlayerDao {
    @Insert
    fun insertAll(players: Set<Player>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(player: Player)

    @Delete
    fun delete(player: Player)

    @Update
    fun update(player: Player)

    @Query("SELECT * FROM player")
    fun getAllPlayers(): Flowable<List<Player>>
}