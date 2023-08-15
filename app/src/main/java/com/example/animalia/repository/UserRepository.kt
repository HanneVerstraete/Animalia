package com.example.animalia.repository

import androidx.lifecycle.MediatorLiveData
import com.example.animalia.database.AnimaliaDatabase
import com.example.animalia.database.users.asDomainModel
import com.example.animalia.domain.User
import com.example.animalia.network.AnimaliaApi
import com.example.animalia.network.users.asDatabaseModel
import com.example.animalia.sharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserRepository(private val database: AnimaliaDatabase) {
    val user = MediatorLiveData<User>()

    suspend fun refreshUser() {
        withContext(Dispatchers.IO) {
            val userFromApi = AnimaliaApi.retrofitService.getUserByEmailAsync(sharedPreferences.emailCurrentUser!!).await()
            Timber.i(userFromApi.toString())
            val userForDatabase = userFromApi.asDatabaseModel()
            Timber.i(userForDatabase.toString())
            database.userDatabaseDao.insert(userForDatabase)
        }
    }

    suspend fun getUserByEmail(email: String): User? {
        return database.userDatabaseDao.getUserByEmail(email)?.asDomainModel()
    }
}