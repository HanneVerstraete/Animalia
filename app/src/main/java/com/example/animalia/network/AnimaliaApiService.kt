package com.example.animalia.network

import com.example.animalia.network.lessons.ApiLesson
import com.example.animalia.network.quizElements.ApiQuizElement
import com.example.animalia.network.users.ApiUser
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

//private const val BASE_URL = "http://192.168.0.176:9000/api/"
//private const val BASE_URL = "http://192.168.68.118:9000/api/"
private const val BASE_URL = "http://192.168.1.46:9000/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val logger = HttpLoggingInterceptor().apply{level = HttpLoggingInterceptor.Level.BASIC}

private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface AnimaliaApiService{
    @GET("lessons")
    fun getLessonsAsync(): Deferred<Array<ApiLesson>>

    @GET("quizElements")
    fun getQuizElementsAsync(): Deferred<Array<ApiQuizElement>>

    @GET("users/{email}")
    fun getUserByEmailAsync(@Path("email") email: String): Deferred<ApiUser>
}

object AnimaliaApi{
    val retrofitService : AnimaliaApiService by lazy {
        retrofit.create(AnimaliaApiService::class.java)
    }
}