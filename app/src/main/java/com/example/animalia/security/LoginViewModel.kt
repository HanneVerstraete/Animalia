package com.example.animalia.security

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.database.users.UserDatabaseDao
import com.example.animalia.network.AnimaliaApi
import com.example.animalia.network.users.asDomainModel
import com.example.animalia.repository.UserRepository
import com.example.animalia.sharedPreferences
import kotlinx.coroutines.launch

class LoginViewModel(database: UserDatabaseDao, application: Application): AndroidViewModel(application) {

    private var sharedPrefs = sharedPreferences
    private val db = AnimaliaDatabase.getInstance(application.applicationContext)
    private val userRepository = UserRepository(db)
    val currentUser = userRepository.user

    fun setUserOnLogin() {
        viewModelScope.launch {
            setUserOnLoginWithRepository()
        }
    }

    suspend private fun setUserOnLoginWithRepository(){
        val user = AnimaliaApi.retrofitService.getUserByEmailAsync(sharedPrefs.emailCurrentUser!!).await().asDomainModel()
        userRepository.user.setValue(user)
        sharedPrefs.firstName = user.firstName
        sharedPrefs.currentLesson = user.lastLessonIndex
        sharedPrefs.currentQuestion = user.lastQuestionIndex
    }

}