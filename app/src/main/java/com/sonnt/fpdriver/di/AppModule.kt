package com.sonnt.fpdriver.di

import com.google.gson.Gson
import com.sonnt.fpdriver.FpDriverApplication
import com.sonnt.fpdriver.data.local.SharedPreferencesApi
import com.sonnt.fpdriver.network.stomp.StompMessageHub

object AppModule {
    private val sharedPreferencesApi by lazy { SharedPreferencesApi() }

    private val gson by lazy { Gson() }

    private val stompMessageHub = StompMessageHub()

    fun provideStompMessageHub() = stompMessageHub

    fun provideGson() = gson

    fun provideApplicationContext() = FpDriverApplication.instance

    fun provideSharedPreferencesApi() = sharedPreferencesApi
}