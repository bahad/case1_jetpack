package com.example.coffeeapp

import android.app.Application
import timber.log.Timber

class CoffeeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}