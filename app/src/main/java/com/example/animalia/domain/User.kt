package com.example.animalia.domain

data class User(
    var id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var lastLessonIndex: Int = 0,
    var lastQuestionIndex: Int = 0
)
