package com.example.animalia.screens.lessonOverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animalia.database.lessons.LessonDatabaseDao

class LessonOverviewViewModelFactory(
    private val dataSource: LessonDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonOverviewViewModel::class.java)) {
            return LessonOverviewViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}