package com.tla.uchattut

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        context = this
    }

    companion object {
        lateinit var context: Context
            private set
    }
}