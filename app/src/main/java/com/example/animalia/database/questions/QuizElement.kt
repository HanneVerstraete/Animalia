package com.example.animalia.database.questions

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_element_table")
data class QuizElement(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quiz_element_id")
    var questionId: Long = 0L,

    @ColumnInfo(name = "quiz_element_index")
    var index: Int = 0,
    @ColumnInfo(name = "quiz_element_question")
    var question: String = "",
    @ColumnInfo(name = "quiz_element_answer")
    var answer: String = "",
    @ColumnInfo(name = "quiz_element_explanation")
    var explanation: String = ""
)