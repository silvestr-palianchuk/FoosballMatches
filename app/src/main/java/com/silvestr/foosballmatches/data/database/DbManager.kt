package com.silvestr.foosballmatches.data.database

import android.content.Context
import androidx.room.Room

object DbManager {
    private const val DB_NAME = "foosball_db"
    lateinit var db: AppDatabase
        private set

    fun init(context: Context) {
        db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
