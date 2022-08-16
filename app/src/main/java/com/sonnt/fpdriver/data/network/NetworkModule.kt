package com.sonnt.fpdriver.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sonnt.fpdriver.data.service.AuthService

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    var moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8081")
        .client(OkHttpClient.Builder().addInterceptor {chain ->
            val token = ""
            val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${token}").build()
            chain.proceed(request)
        }.build())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    var authService: AuthService = retrofit.create(AuthService::class.java)
}