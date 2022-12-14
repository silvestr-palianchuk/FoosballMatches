package com.silvestr.foosballmatches.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.silvestr.foosballmatches.data.database.AppDatabase
import com.silvestr.foosballmatches.data.database.DbManager
import com.silvestr.foosballmatches.data.repository.FoosballRepositoryImpl
import com.silvestr.foosballmatches.domain.FoosballRepository
import com.silvestr.foosballmatches.domain.GamesInteractor
import com.silvestr.foosballmatches.domain.PlayersInteractor
import com.silvestr.foosballmatches.domain.RankingsInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDataBase(context: Context): AppDatabase {
        DbManager.init(context)
        return DbManager.db
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideFoosballRepository(database: AppDatabase): FoosballRepository {
        return FoosballRepositoryImpl(database)
    }

    @Provides
    fun provideGamesInteractor(foosballRepository: FoosballRepository): GamesInteractor {
        return GamesInteractor(foosballRepository)
    }

    @Provides
    fun providePlayersInteractor(foosballRepository: FoosballRepository): PlayersInteractor {
        return PlayersInteractor(foosballRepository)
    }

    @Provides
    fun provideRankingsInteractor(foosballRepository: FoosballRepository): RankingsInteractor {
        return RankingsInteractor(foosballRepository)
    }

}