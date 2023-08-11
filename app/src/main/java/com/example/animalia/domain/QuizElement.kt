package com.example.animalia.domain

data class QuizElement (
    var questionId: Long = 0L,
    var index: Int = 0,
    var question: String = "",
    var answer: String = "",
    var explanation: String = ""
)