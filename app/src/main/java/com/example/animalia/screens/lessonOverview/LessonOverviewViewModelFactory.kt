package com.example.animalia.screens.lessonOverview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LessonOverviewViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LessonOverviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LessonOverviewViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}