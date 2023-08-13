package com.example.animalia.screens.truefalse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animalia.repository.QuizElementRepository

class TruefalseViewModelFactory(val app: QuizElementRepository, val index: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TruefalseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TruefalseViewModel(app, index) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}