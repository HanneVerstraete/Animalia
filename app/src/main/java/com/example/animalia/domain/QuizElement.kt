package com.example.animalia.domain

data class QuizElement (
    var questionId: String = "",
    var index: Int = 0,
    var question: String = "",
    var answer: String = "",
    var explanation: String = ""
)