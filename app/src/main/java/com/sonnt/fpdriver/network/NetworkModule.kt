package com.sonnt.fpdriver.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sonnt.fpdriver.data.local.AuthDataSource
import com.sonnt.fpdriver.data.local.SharedPreferencesApi
import com.sonnt.fpdriver.di.AppModule
import com.sonnt.fpdriver.network.service.AuthService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://ec90-2402-800-6172-89f3-d72-a379-4153-251.ap.ngrok.io")
        .client(OkHttpClient.Builder().addInterceptor {chain ->
            val token = AuthDataSource.authToken
            val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${token}").build()
            chain.proceed(request)
        }.build())
        .addConverterFactory(GsonConverterFactory.create(AppModule.provideGson()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    var authService: AuthService = retrofit.create(AuthService::class.java)
}