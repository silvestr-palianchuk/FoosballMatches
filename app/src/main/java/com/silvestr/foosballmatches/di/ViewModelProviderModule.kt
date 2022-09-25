package com.silvestr.foosballmatches.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.ui.games.GamesViewModel
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

}