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

    @Query("SELECT * FROM lesson_table ORDER BY lesson_index ASC")
    fun getAllLessonsLive(): LiveData<Array<DatabaseLesson>>

    @Query("SELECT * FROM lesson_table WHERE lesson_index < :currentLesson ORDER BY lesson_index ASC")
    fun getDoneLessonsLive(currentLesson: Int): LiveData<Array<DatabaseLesson>>

    @Query("SELECT * FROM lesson_table WHERE lesson_index >= :currentLesson ORDER BY lesson_index ASC")
    fun getNewLessonsLive(currentLesson: Int): LiveData<Array<DatabaseLesson>>

    @Query("SELECT * from lesson_table WHERE lesson_index = :index")
    suspend fun getLessonByIndex(index: Int): DatabaseLesson

    @Query("SELECT COUNT(*) FROM lesson_table")
    suspend fun getLessonCount(): Int
}