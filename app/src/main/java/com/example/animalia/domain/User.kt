package com.example.animalia.domain

import com.example.animalia.network.users.ApiUser

data class User(
    var id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var lastLessonIndex: Int = 0,
    var lastQuestionIndex: Int = 0,
    var level: Int = 0,
    var xp: Int = 0
)

fun User.asApiModel(): ApiUser {
    return ApiUser(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        lastLessonIndex = lastLessonIndex,
        lastQuestionIndex = lastQuestionIndex,
        level = level,
        xp = xp
    )
}