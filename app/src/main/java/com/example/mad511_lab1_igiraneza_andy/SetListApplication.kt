package com.example.mad511_lab1_igiraneza_andy

import android.app.Application

class SetListApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()

        container = AppContainer(this)
    }
}