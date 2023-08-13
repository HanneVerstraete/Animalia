package com.example.animalia.screens.lesson

import android.app.Application
import androidx.lifecycle.*
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.domain.Lesson
import com.example.animalia.repository.LessonRepository
import com.example.animalia.sharedPreferences
import kotlinx.coroutines.launch

class LessonViewModel(application: Application) : AndroidViewModel(application) {
    var lessonNumber: Int = sharedPreferences.currentLesson

    private var _currentLesson = MutableLiveData<Lesson>()
    val currentLesson: LiveData<Lesson>
        get() = _currentLesson

    private val _shouldEnd = MutableLiveData<Boolean>()
    val shouldEnd: LiveData<Boolean>
        get() = _shouldEnd

    private val _isFinished = MutableLiveData<Boolean>()
    val isFinished: LiveData<Boolean>
        get() = _isFinished


    private val database = AnimaliaDatabase.getInstance(application.applicationContext)
    private val lessonRepository = LessonRepository(database)
    private var totalNumberOfLessons = 0

    init {
        _isFinished.value = isLastLesson(lessonNumber)
        initializeLiveData()
    }

    private fun initializeLiveData() {
        viewModelScope.launch {
            if (!isFinished.value!!) {
                lessonRepository.refreshLessons()
                _currentLesson.value = lessonRepository.getLessonByIndex(lessonNumber)
                totalNumberOfLessons = lessonRepository.getLessonCount()
            }

        }
    }

    fun getNextLesson() {
        lessonNumber++
        if (isLastLesson(lessonNumber)) {
            _shouldEnd.value = true
        } else {
            viewModelScope.launch {
                _currentLesson.value = lessonRepository.getLessonByIndex(lessonNumber)
            }
        }
    }

    private fun isLastLesson(lessonNumber: Int): Boolean {
        return totalNumberOfLessons - 1 <= lessonNumber
    }
}