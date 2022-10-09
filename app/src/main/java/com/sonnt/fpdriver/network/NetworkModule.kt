package com.sonnt.fpdriver.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sonnt.fpdriver.data.local.AuthDataSource
import com.sonnt.fpdriver.data.local.SharedPreferencesApi
import com.sonnt.fpdriver.di.AppModule
import com.sonnt.fpdriver.network.service.AuthService
import com.sonnt.fpdriver.network.service.StatusService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Endpoint.BASE_URL)
        .client(OkHttpClient.Builder().addInterceptor {chain ->
            val token = AuthDataSource.authToken
            val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${token}").build()
            chain.proceed(request)
        }.build())
        .addConverterFactory(GsonConverterFactory.create(AppModule.provideGson()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    var authService: AuthService = retrofit.create(AuthService::class.java)
    var statusService: StatusService = retrofit.create(StatusService::class.java)
}