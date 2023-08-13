package com.example.animalia.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.repository.LessonRepository
import com.example.animalia.sharedPreferences
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val database = AnimaliaDatabase.getInstance(application.applicationContext)
    private val lessonRepository = LessonRepository(database)

    private val _isFinishedLessons = MutableLiveData<Boolean>()
    val isFinishedLessons: LiveData<Boolean>
        get() = _isFinishedLessons

    init {
        initializeLiveData()
    }

    private fun initializeLiveData() {
        viewModelScope.launch {
            lessonRepository.refreshLessons()
            val currentUserLesson = sharedPreferences.currentLesson
            val totalLessons = lessonRepository.getLessonCount()

            _isFinishedLessons.value = currentUserLesson >= totalLessons
        }
    }
}