package com.example.animalia.screens.truefalse

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TruefalseViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TruefalseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TruefalseViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}