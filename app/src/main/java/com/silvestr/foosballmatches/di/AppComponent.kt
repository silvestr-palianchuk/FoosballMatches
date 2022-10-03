package com.silvestr.foosballmatches.di

import android.content.Context
import com.silvestr.foosballmatches.ui.games.AddGameDialogFragment
import com.silvestr.foosballmatches.ui.games.EditGameDialogFragment
import com.silvestr.foosballmatches.ui.games.GamesFragment
import com.silvestr.foosballmatches.ui.players.PlayersFragment
import com.silvestr.foosballmatches.ui.rankings.RankingsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ViewModelProviderModule::class])
interface AppComponent {

    fun inject(gamesFragment: GamesFragment)
    fun inject(editGameDialogFragment: EditGameDialogFragment)
    fun inject(addGameDialogFragment: AddGameDialogFragment)
    fun inject(playersFragment: PlayersFragment)
    fun inject(rankingsFragment: RankingsFragment)


    @Component.Builder
    interface AppComponentBuilder {
        fun buildAppComponent(): AppComponent

        /*
        * Passing ApplicationContext to AppComponent in order to use it in any modules
        * */
        @BindsInstance
        fun context(context: Context): AppComponentBuilder
    }
}