package com.silvestr.foosballmatches

import android.app.Application
import com.silvestr.foosballmatches.data.database.DbManager
import com.silvestr.foosballmatches.di.AppComponent
import com.silvestr.foosballmatches.di.DaggerAppComponent


class FoosballApplication : Application() {

    val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .context(this)
            .buildAppComponent()
    }

    override fun onCreate() {
        super.onCreate()
        DbManager.init(this)
    }
}