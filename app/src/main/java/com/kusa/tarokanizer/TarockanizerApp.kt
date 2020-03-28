package com.kusa.tarokanizer

import android.app.Application
import android.content.Context

class TarockanizerApp : Application() {

    companion object {
        lateinit var instance: Context
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}