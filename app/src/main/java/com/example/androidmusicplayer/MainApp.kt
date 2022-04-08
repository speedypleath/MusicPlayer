package com.example.androidmusicplayer

import android.app.Application
import com.example.androidmusicplayer.di.Koin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Koin().start(this@MainApp)
        instance = this
    }

    companion object {
        lateinit var instance: MainApp
    }
}