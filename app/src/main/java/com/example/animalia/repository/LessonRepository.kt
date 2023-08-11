package com.example.animalia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.database.lessons.asDomainModel
import com.example.animalia.domain.Lesson
import com.example.animalia.network.AnimaliaApi
import com.example.animalia.network.lessons.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class LessonRepository(private val database: AnimaliaDatabase) {
    val lessons: LiveData<Array<Lesson>> =
        Transformations.map(database.lessonDatabaseDao.getAllLessonsLive()){
            it.asDomainModel()
        }
    // TODO get actual currentLesson
    val doneLessons: LiveData<Array<Lesson>> =
        Transformations.map(database.lessonDatabaseDao.getDoneLessonsLive(1)){
            it.asDomainModel()
        }
    // TODO get actual currentLesson
    val newLessons: LiveData<Array<Lesson>> =
        Transformations.map(database.lessonDatabaseDao.getNewLessonsLive(1)){
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
}