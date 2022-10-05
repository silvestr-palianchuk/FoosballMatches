package com.silvestr.foosballmatches.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player


@Database(entities = [(Game::class), (Player::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val gameDao: GameDao
    abstract val playerDao: PlayerDao
}