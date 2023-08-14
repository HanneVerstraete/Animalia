package com.example.animalia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.database.questions.asDomainModel
import com.example.animalia.domain.QuizElement
import com.example.animalia.network.AnimaliaApi
import com.example.animalia.network.quizElements.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class QuizElementRepository(private val database: AnimaliaDatabase) {
    val quizElements: LiveData<Array<QuizElement>> =
        Transformations.map(database.quizElementDatabaseDao.getAllQuizElementsLive()){
            it.asDomainModel()
        }

    suspend fun refreshQuizElements(){
        withContext(Dispatchers.IO){
            val quizElements = AnimaliaApi.retrofitService.getQuizElementsAsync().await()
            database.quizElementDatabaseDao.insertAll(*quizElements.asDatabaseModel())
            Timber.i("end suspend")
        }
    }

    suspend fun getQuizElementByIndex(index: Int): QuizElement? {
        return database.quizElementDatabaseDao.getByIndex(index)?.asDomainModel()
    }

    suspend fun getQuizElementCount(): Int {
        return database.quizElementDatabaseDao.getNumberOfQuizElements()
    }
}