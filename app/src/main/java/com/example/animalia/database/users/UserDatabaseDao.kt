package com.example.animalia.database.users

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDatabaseDao {
    @Insert
    suspend fun insert(user: DatabaseUser)

    @Update
    suspend fun update(user: DatabaseUser)

    @Query("SELECT * from user_table WHERE user_id = :key")
    suspend fun get(key: Long): DatabaseUser?

    @Query("DELETE FROM user_table")
    suspend fun clear()

    @Query("SELECT * FROM user_table ORDER BY user_id DESC")
    suspend fun getAllUsers(): List<DatabaseUser>

    @Query("SELECT * FROM user_table ORDER BY user_id DESC")
    fun getAllUsersAsLiveData(): LiveData<List<DatabaseUser>>

}