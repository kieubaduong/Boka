package com.example.bookreadingapplication.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = "https://rseeshop-api.onrender.com/api/"
    private var newTokem : Boolean = false

    var token : String = "Init token"
        get() = "Init token"
        set(value)  {
            buildRetrofit()
            field = value
        }

    private var retrofit: Retrofit? = null

    private val tokenInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        chain.proceed(newRequest)
    }

    private fun buildRetrofit() {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(tokenInterceptor)
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getAuthService(): AuthService {
        if (retrofit == null) {
            buildRetrofit()
        }
        return retrofit!!.create(AuthService::class.java)
    }
}