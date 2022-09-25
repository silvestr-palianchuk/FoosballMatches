package com.silvestr.foosballmatches.di

import android.content.Context
import com.silvestr.foosballmatches.ui.games.GamesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ViewModelProviderModule::class])
interface AppComponent {

    fun inject(amesFragment: GamesFragment)


    @Component.Builder
    interface AppComponentBuilder {
        fun buildAppComponent(): AppComponent

        @BindsInstance
        fun context(context: Context): AppComponentBuilder
    }
}