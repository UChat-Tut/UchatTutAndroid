package com.tla.uchattut

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import leakcanary.LeakCanary


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        LeakCanary.config = LeakCanary.config.copy(retainedVisibleThreshold = 1)

        context = this
    }

    companion object {
        lateinit var context: Context
            private set
    }
}