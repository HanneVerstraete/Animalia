package com.example.animalia.database.questions

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animalia.domain.QuizElement

@Entity(tableName = "quiz_element_table")
data class DatabaseQuizElement(
    @PrimaryKey
    @ColumnInfo(name = "quiz_element_id")
    var questionId: String = "",

    @ColumnInfo(name = "quiz_element_index")
    var index: Int = 0,
    @ColumnInfo(name = "quiz_element_question")
    var question: String = "",
    @ColumnInfo(name = "quiz_element_answer")
    var answer: String = "",
    @ColumnInfo(name = "quiz_element_explanation")
    var explanation: String = ""
)

fun Array<DatabaseQuizElement>.asDomainModel(): Array<QuizElement> {
    return map {
        QuizElement(
            index = it.index,
            question = it.question,
            answer = it.answer,
            explanation = it.explanation
        )
    }.toTypedArray()
}

fun DatabaseQuizElement.asDomainModel(): QuizElement {
    return QuizElement(
        index = index,
        question = question,
        answer = answer,
        explanation = explanation
    )
}