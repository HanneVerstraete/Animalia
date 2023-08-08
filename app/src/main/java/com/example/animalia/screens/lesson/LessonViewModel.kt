package com.example.animalia.screens.lesson

import android.app.Application
import androidx.lifecycle.*
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.repository.LessonRepository
import kotlinx.coroutines.launch

class LessonViewModel(application: Application) : AndroidViewModel(application) {
    var lessonNumber: Int = 0

//    private var _currentLesson = MutableLiveData<String>()
//    val currentLesson: LiveData<String>
//        get() = _currentLesson

    private val _shouldEnd = MutableLiveData<Boolean>()
    val shouldEnd: LiveData<Boolean>
        get() = _shouldEnd

    private val database = AnimaliaDatabase.getInstance(application.applicationContext)
    private val lessonRepository = LessonRepository(database)

    val lessons = lessonRepository.lessons

    init {
        initializeLiveData()
    }

    private fun initializeLiveData() {
        viewModelScope.launch {
            lessonRepository.refreshLessons()
        }
    }

    // TODO handle when no more lessons
    fun getNextLesson() {
        if (isLastLesson(lessonNumber)) {
            _shouldEnd.value = true
        } else {
            lessonNumber++
        }
    }

    fun isLastLesson(lessonNumber: Int): Boolean {
        return (lessons.value?.size?.minus(1) ?: 100) < lessonNumber
    }
}