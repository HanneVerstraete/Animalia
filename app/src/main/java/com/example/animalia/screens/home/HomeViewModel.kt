package com.example.animalia.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.repository.LessonRepository
import com.example.animalia.repository.QuizElementRepository
import com.example.animalia.sharedPreferences
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val database = AnimaliaDatabase.getInstance(application.applicationContext)
    private val lessonRepository = LessonRepository(database)
    private val quizElementRepository = QuizElementRepository(database)

    private val _isFinishedLessons = MutableLiveData<Boolean>()
    val isFinishedLessons: LiveData<Boolean>
        get() = _isFinishedLessons

    private val _isFinishedQuizElements = MutableLiveData<Boolean>()
    val isFinishedQuizElements: LiveData<Boolean>
        get() = _isFinishedQuizElements

    init {
        initializeLiveData()
    }

    private fun initializeLiveData() {
        viewModelScope.launch {
            lessonRepository.refreshLessons()
            val currentUserLesson = sharedPreferences.currentLesson
            val totalLessons = lessonRepository.getLessonCount()
            _isFinishedLessons.value = currentUserLesson >= totalLessons

            quizElementRepository.refreshQuizElements()
            val currentUserQuizElement = sharedPreferences.currentQuestion
            val totalQuizElements = quizElementRepository.getQuizElementCount()
            _isFinishedQuizElements.value = currentUserQuizElement + 3 > totalQuizElements
        }
    }
}