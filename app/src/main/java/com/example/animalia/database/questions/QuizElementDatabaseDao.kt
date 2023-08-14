package com.example.animalia.database.questions

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuizElementDatabaseDao {
    @Insert
    suspend fun insert(quiz: DatabaseQuizElement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg quizElement: DatabaseQuizElement)

    @Update
    suspend fun update(quiz: DatabaseQuizElement)

    @Query("SELECT * from quiz_element_table WHERE quiz_element_id = :key")
    suspend fun get(key: String): DatabaseQuizElement?

    @Query("DELETE FROM quiz_element_table")
    suspend fun clear()

    @Query("SELECT * FROM quiz_element_table ORDER BY quiz_element_index ASC")
    suspend fun getAllQuizElements(): List<DatabaseQuizElement>

    @Query("SELECT * FROM quiz_element_table ORDER BY quiz_element_index ASC")
    fun getAllQuizElementsLive(): LiveData<Array<DatabaseQuizElement>>

    @Query("SELECT COUNT(*) FROM quiz_element_table")
    suspend fun getNumberOfQuizElements(): Int

    @Query("SELECT * from quiz_element_table WHERE quiz_element_index = :index")
    suspend fun getByIndex(index: Int): DatabaseQuizElement?
}