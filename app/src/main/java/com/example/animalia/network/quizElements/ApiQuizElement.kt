package com.example.animalia.network.quizElements

import com.example.animalia.database.questions.DatabaseQuizElement
import com.example.animalia.domain.QuizElement

data class ApiQuizElement(
    var id: String = "",
    var index: Int = 0,
    var question: String = "",
    var answer: String = "",
    var explanation: String = ""
)

fun Array<ApiQuizElement>.asDomainModel(): Array<QuizElement> {
    return map {
        QuizElement(
            questionId = it.id,
            index = it.index,
            question = it.question,
            answer = it.answer,
            explanation = it.explanation
        )
    }.toTypedArray()
}

fun Array<ApiQuizElement>.asDatabaseModel(): Array<DatabaseQuizElement> {
    return map {
        DatabaseQuizElement(
            questionId = it.id,
            index = it.index,
            question = it.question,
            answer = it.answer,
            explanation = it.explanation
        )
    }.toTypedArray()
}

fun ApiQuizElement.asDatabaseQuizElement(): DatabaseQuizElement {
    return DatabaseQuizElement(
        questionId = id,
        index = index,
        question = question,
        answer = answer,
        explanation = explanation
    )
}