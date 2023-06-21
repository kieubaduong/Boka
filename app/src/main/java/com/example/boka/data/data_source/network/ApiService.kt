package com.example.boka.data.data_source.network

import com.example.boka.data.data_source.network.auth.AuthService
import com.example.boka.data.data_source.network.book.BookService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = "https://rseeshop-api.onrender.com/api/"
    private var newToken : Boolean = false
    var token : String = "Init token"
        set(value)  {
            field = value
            retrofit = null
        }

    private var retrofit: Retrofit? = null
    val authService: AuthService by lazy {
        if (retrofit == null) {
            buildRetrofit()
        }
        retrofit!!.create(AuthService::class.java)
    }
    val bookService : BookService by lazy {
        if (retrofit == null) {
            buildRetrofit()
        }
        retrofit!!.create(BookService::class.java)
    }

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
}