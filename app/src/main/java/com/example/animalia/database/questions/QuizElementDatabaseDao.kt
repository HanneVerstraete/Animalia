package com.example.animalia.database.questions

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuizElementDatabaseDao {
    // TODO use liveData?
    @Insert
    suspend fun insert(quiz: QuizElement)

    @Update
    suspend fun update(quiz: QuizElement)

    @Query("SELECT * from quiz_element_table WHERE quiz_element_id = :key")
    suspend fun get(key: Long): QuizElement?

    @Query("DELETE FROM quiz_element_table")
    suspend fun clear()

    @Query("SELECT * FROM quiz_element_table ORDER BY quiz_element_id DESC")
    suspend fun getAllQuizElements(): List<QuizElement>
//    suspend fun getAllQuizElements(): LiveData<List<QuizElement>>

    @Query("SELECT COUNT(*) FROM quiz_element_table")
    suspend fun numberOfQuizElements(): Int

    @Query("SELECT * from quiz_element_table WHERE quiz_element_index = :index")
    suspend fun getByIndex(index: Int): QuizElement
}