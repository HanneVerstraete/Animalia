package com.example.animalia.screens.truefalse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animalia.repository.QuizElementRepository
import com.example.animalia.repository.UserRepository

class TruefalseViewModelFactory(private val quizElementRepository: QuizElementRepository, val userRepository: UserRepository, val index: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TruefalseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TruefalseViewModel(quizElementRepository, userRepository, index) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}