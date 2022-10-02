package com.silvestr.foosballmatches.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.silvestr.foosballmatches.ui.games.GamesViewModel
import com.silvestr.foosballmatches.ui.players.PlayersViewModel
import com.silvestr.foosballmatches.ui.rankings.RankingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelProviderModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GamesViewModel::class)
    abstract fun gamesViewModel(viewModel: GamesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlayersViewModel::class)
    abstract fun playersViewModel(viewModel: PlayersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RankingsViewModel::class)
    abstract fun rankingsViewModel(viewModel: RankingsViewModel): ViewModel

}