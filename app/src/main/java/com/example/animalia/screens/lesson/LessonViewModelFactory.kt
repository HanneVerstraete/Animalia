package com.example.animalia.screens.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animalia.repository.LessonRepository

class LessonViewModelFactory(private val lessonRepository: LessonRepository, val index: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LessonViewModel(lessonRepository, index) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}