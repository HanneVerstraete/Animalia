package com.example.animalia.screens.lesson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalia.domain.Lesson
import com.example.animalia.repository.LessonRepository
import com.example.animalia.repository.UserRepository
import kotlinx.coroutines.launch
import kotlin.math.ceil

class LessonViewModel(
    private val lessonRepository: LessonRepository,
    private val userRepository: UserRepository,
    private var lessonNumber: Int
) : ViewModel() {
    private var _currentLesson = MutableLiveData<Lesson>()
    val currentLesson: LiveData<Lesson>
        get() = _currentLesson

    var totalNumberOfLessons = 0

    init {
        initializeLiveData()
    }

    private fun initializeLiveData() {
        viewModelScope.launch {
            lessonRepository.refreshLessons()
            userRepository.refreshUser()
            totalNumberOfLessons = lessonRepository.getLessonCount()
            if (!isFinished(lessonNumber)) {
                _currentLesson.value = lessonRepository.getLessonByIndex(lessonNumber)
            }
        }
    }

    fun getNextLesson() {
        lessonNumber++
        updateUser()
        if (!isFinished(lessonNumber)) {
            viewModelScope.launch {
                _currentLesson.value = lessonRepository.getLessonByIndex(lessonNumber)
            }
        }
    }

    fun getPreviousLesson() {
        lessonNumber--
        viewModelScope.launch {
            _currentLesson.value = lessonRepository.getLessonByIndex(lessonNumber)
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            val user = userRepository.getUser()
            if (user!!.lastLessonIndex < lessonNumber) {
                user.lastLessonIndex = lessonNumber
                user.xp = user.xp + 2
                user.level = ceil((user.xp).toDouble() / 10).toInt()
                userRepository.updateUser(user)
            }
        }
    }

    private fun isFinished(lessonNumber: Int): Boolean {
        return totalNumberOfLessons <= lessonNumber
    }
}