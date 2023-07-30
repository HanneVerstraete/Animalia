package com.example.animalia.screens.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animalia.database.lessons.LessonDatabaseDao

class LessonViewModelFactory(
    private val dataSource: LessonDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonViewModel::class.java)) {
            return LessonViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}