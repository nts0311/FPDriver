package com.sonnt.fpdriver

import android.app.Application

class FpDriverApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: FpDriverApplication
    }
}