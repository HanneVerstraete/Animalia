package com.example.animalia.screens.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animalia.repository.LessonRepository
import com.example.animalia.repository.UserRepository

class LessonViewModelFactory(private val lessonRepository: LessonRepository, private val userRepository: UserRepository, val index: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LessonViewModel(lessonRepository, userRepository, index) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}