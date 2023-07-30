package com.example.animalia.database.lessons

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LessonDatabaseDao {
    @Insert
    suspend fun insert(lesson: Lesson)

    @Update
    suspend fun update(lesson: Lesson)

    @Query("SELECT * from lesson_table WHERE lesson_id = :key")
    suspend fun get(key: Long): Lesson?

    @Query("DELETE FROM lesson_table")
    suspend fun clear()

    @Query("SELECT * FROM lesson_table ORDER BY lesson_id DESC")
    suspend fun getAllLessons(): List<Lesson>

    @Query("SELECT * FROM lesson_table ORDER BY lesson_index ASC")
    fun getAllLessonsAsLiveData(): LiveData<List<Lesson>>

    @Query("SELECT * from lesson_table WHERE lesson_index = :index")
    suspend fun getLessonByIndex(index: Int): Lesson
}