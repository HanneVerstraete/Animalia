package com.example.animalia.database.lessons

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LessonDatabaseDao {
    @Insert
    suspend fun insert(lesson: DatabaseLesson)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg lesson : DatabaseLesson)

    @Update
    suspend fun update(lesson: DatabaseLesson)

    @Query("SELECT * from lesson_table WHERE lesson_id = :key")
    suspend fun get(key: Long): DatabaseLesson?

    @Query("DELETE FROM lesson_table")
    suspend fun clear()

    @Query("SELECT * FROM lesson_table ORDER BY lesson_id ASC")
    suspend fun getAllLessons(): Array<DatabaseLesson>

    // does return type need to be arrya instead of list?
    @Query("SELECT * FROM lesson_table ORDER BY lesson_index ASC")
    fun getAllLessonsLive(): LiveData<Array<DatabaseLesson>>

    @Query("SELECT * from lesson_table WHERE lesson_index = :index")
    suspend fun getLessonByIndex(index: Int): DatabaseLesson
}