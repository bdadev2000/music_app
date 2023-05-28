package com.bdadev.musicplayer.application

import android.app.Application
import com.bdadev.musicplayer.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@MyApplication)
            modules(appModules)
        }
    }

    companion object {
        private var instance: MyApplication? = null
    }
}