package com.example.animalia.screens.lesson

import android.app.Application
import androidx.lifecycle.*
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.domain.Lesson
import com.example.animalia.repository.LessonRepository
import kotlinx.coroutines.launch

class LessonViewModel(application: Application) : AndroidViewModel(application) {
    var lessonNumber: Int = 0

    private var _currentLesson = MutableLiveData<Lesson>()
    val currentLesson: LiveData<Lesson>
        get() = _currentLesson

    private val _shouldEnd = MutableLiveData<Boolean>()
    val shouldEnd: LiveData<Boolean>
        get() = _shouldEnd

    private val database = AnimaliaDatabase.getInstance(application.applicationContext)
    private val lessonRepository = LessonRepository(database)

    init {
        initializeLiveData()
    }

    private fun initializeLiveData() {
        viewModelScope.launch {
            lessonRepository.refreshLessons()
            _currentLesson.value = lessonRepository.getLessonByIndex(lessonNumber)
        }
    }

    // TODO handle when no more lessons
    fun getNextLesson() {
        if (isLastLesson(lessonNumber)) {
            _shouldEnd.value = true
        } else {
            lessonNumber++
            viewModelScope.launch {
                _currentLesson.value = lessonRepository.getLessonByIndex(lessonNumber)
            }
        }
    }

    fun isLastLesson(lessonNumber: Int): Boolean {
        // TODO handle properly
        return false
//        return (lessons.value?.size?.minus(1) ?: 100) < lessonNumber
    }
}