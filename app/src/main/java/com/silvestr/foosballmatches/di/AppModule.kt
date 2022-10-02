package com.silvestr.foosballmatches.di

import com.silvestr.foosballmatches.data.repository.FoosballRepositoryImpl
import com.silvestr.foosballmatches.domain.FoosballRepository
import com.silvestr.foosballmatches.domain.GamesInteractor
import com.silvestr.foosballmatches.domain.PlayersInteractor
import dagger.Module
import dagger.Provides


@Module
class AppModule {

    @Provides
    fun provideFoosballRepository(): FoosballRepository {
        return FoosballRepositoryImpl()
    }

    @Provides
    fun provideGamesInteractor(foosballRepository: FoosballRepository): GamesInteractor {
        return GamesInteractor(foosballRepository)
    }

    @Provides
    fun providePlayersInteractor(foosballRepository: FoosballRepository): PlayersInteractor {
        return PlayersInteractor(foosballRepository)
    }

}