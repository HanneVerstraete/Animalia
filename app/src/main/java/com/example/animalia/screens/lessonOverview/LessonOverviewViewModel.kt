package com.example.animalia.screens.lessonOverview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.repository.LessonRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

class LessonOverviewViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AnimaliaDatabase.getInstance(application.applicationContext)
    private val lessonRepository = LessonRepository(database)

    private val _filter = MutableLiveData<String?>(null)

    val lessons = Transformations.switchMap(_filter) {
        when (it) {
            "gedaan" -> lessonRepository.doneLessons
            "te doen" -> lessonRepository.newLessons
            else -> lessonRepository.lessons
        }
    }

    init {
        Timber.i("getting lessons")
        viewModelScope.launch {
            lessonRepository.refreshLessons()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun filterChip(tag: String, checked: Boolean) {
        _filter.value = if (checked) tag else null
    }
}