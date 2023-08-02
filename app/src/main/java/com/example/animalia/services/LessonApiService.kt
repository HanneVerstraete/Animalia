package com.example.animalia.services

import com.example.animalia.database.lessons.Lesson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface LessonApiService {
    @GET("lessons")
    fun getLessonsAsync(): Deferred<Array<Lesson>>

    companion object {
        private const val BASE_URL = "http://192.168.0.176:9000/api/"

        val INSTANCE: LessonApiService by lazy {
            createRetrofitService()
        }

        private fun createRetrofitService(): LessonApiService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(LessonApiService::class.java)
        }
    }
}