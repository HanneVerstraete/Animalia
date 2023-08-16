package com.example.animalia.screens.lesson

import androidx.lifecycle.*
import com.example.animalia.domain.Lesson
import com.example.animalia.repository.LessonRepository
import com.example.animalia.repository.UserRepository
import kotlinx.coroutines.launch

class LessonViewModel(
    private val lessonRepository: LessonRepository,
    private val userRepository: UserRepository,
    private var lessonNumber: Int
) : ViewModel() {
    private var _currentLesson = MutableLiveData<Lesson>()
    val currentLesson: LiveData<Lesson>
        get() = _currentLesson

    private val _isDoingLastLesson = MutableLiveData<Boolean>()
    val isDoingLastLesson: LiveData<Boolean>
        get() = _isDoingLastLesson

    private val _isFinished = MutableLiveData<Boolean>()
    val isFinished: LiveData<Boolean>
        get() = _isFinished

    private var totalNumberOfLessons = 0

    init {
        initializeLiveData()
    }

    private fun initializeLiveData() {
        viewModelScope.launch {
            lessonRepository.refreshLessons()
            userRepository.refreshUser()
            totalNumberOfLessons = lessonRepository.getLessonCount()
            _isDoingLastLesson.value = isDoingLastLesson(lessonNumber)
            _isFinished.value = isFinished(lessonNumber)
            if (!isFinished.value!!) {
                _currentLesson.value = lessonRepository.getLessonByIndex(lessonNumber)
            }
        }
    }

    fun getNextLesson() {
        lessonNumber++
        viewModelScope.launch {
            val user = userRepository.getUser()
            if (user!!.lastLessonIndex < lessonNumber) {
                user.lastLessonIndex = lessonNumber
                userRepository.updateUser(user)
            }
        }
        if (isFinished(lessonNumber)) {
            _isFinished.value = true
        } else {
            _isDoingLastLesson.value = isDoingLastLesson(lessonNumber)
            viewModelScope.launch {
                _currentLesson.value = lessonRepository.getLessonByIndex(lessonNumber)
            }
        }
    }

    private fun isDoingLastLesson(lessonNumber: Int): Boolean {
        return totalNumberOfLessons - 1 <= lessonNumber
    }

    private fun isFinished(lessonNumber: Int): Boolean {
        return totalNumberOfLessons <= lessonNumber
    }
}