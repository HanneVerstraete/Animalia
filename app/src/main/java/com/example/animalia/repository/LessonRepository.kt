package com.example.animalia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.database.lessons.asDomainModel
import com.example.animalia.domain.Lesson
import com.example.animalia.network.AnimaliaApi
import com.example.animalia.network.lessons.asDatabaseModel
import com.example.animalia.sharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class LessonRepository(private val database: AnimaliaDatabase) {
    val lessons: LiveData<Array<Lesson>> =
        Transformations.map(database.lessonDatabaseDao.getAllLessonsLive()){
            it.asDomainModel()
        }

    val doneLessons: LiveData<Array<Lesson>> =
        Transformations.map(database.lessonDatabaseDao.getDoneLessonsLive(sharedPreferences.currentLesson)){
            it.asDomainModel()
        }

    val newLessons: LiveData<Array<Lesson>> =
        Transformations.map(database.lessonDatabaseDao.getNewLessonsLive(sharedPreferences.currentLesson)){
            it.asDomainModel()
        }


    suspend fun refreshLessons(){
        withContext(Dispatchers.IO){
            val lessons = AnimaliaApi.retrofitService.getLessonsAsync().await()
            database.lessonDatabaseDao.insertAll(*lessons.asDatabaseModel())
            Timber.i("end suspend")
        }
    }

    suspend fun getLessonByIndex(index: Int): Lesson{
        return database.lessonDatabaseDao.getLessonByIndex(index).asDomainModel()
    }

    suspend fun getLessonCount(): Int {
        return database.lessonDatabaseDao.getLessonCount()
    }
}