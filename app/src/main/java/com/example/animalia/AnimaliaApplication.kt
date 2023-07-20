package com.example.animalia

import android.app.Application
import timber.log.Timber

class AnimaliaApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}