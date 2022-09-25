package com.silvestr.foosballmatches.di

import com.silvestr.foosballmatches.data.repository.FoosballRepositoryImpl
import com.silvestr.foosballmatches.domain.FoosballRepository
import com.silvestr.foosballmatches.domain.GetGamesInteractor
import dagger.Module
import dagger.Provides


@Module
class AppModule {

    @Provides
    fun provideFoosballRepository(): FoosballRepository {
        return FoosballRepositoryImpl()
    }

    @Provides
    fun provideGamesInteractor(foosballRepository: FoosballRepository): GetGamesInteractor {
        return GetGamesInteractor(foosballRepository)
    }
}