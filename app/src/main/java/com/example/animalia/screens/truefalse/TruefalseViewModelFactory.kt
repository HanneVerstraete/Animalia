package com.example.animalia.screens.truefalse

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animalia.database.questions.QuizElementDatabaseDao

class TruefalseViewModelFactory(
    private val dataSource: QuizElementDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TruefalseViewModel::class.java)) {
            return TruefalseViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}