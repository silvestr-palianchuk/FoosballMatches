package com.silvestr.foosballmatches.data.database

import androidx.room.*
import com.silvestr.foosballmatches.data.Game

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(games: List<Game>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: Game)

    @Delete
    fun delete(game: Game)

    @Query("DELETE from game WHERE id =:id")
    fun deleteById(id: Int)

    @Update
    fun update(game: Game)

    @Query("SELECT * FROM game")
    fun getAllGames(): List<Game>?
}