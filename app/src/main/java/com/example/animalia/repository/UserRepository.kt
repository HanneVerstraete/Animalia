package com.example.animalia.repository

import androidx.lifecycle.MediatorLiveData
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.domain.User

class UserRepository(private val database: AnimaliaDatabase) {
    val user = MediatorLiveData<User>()
}