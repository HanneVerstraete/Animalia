package com.example.animalia.network.users

import com.example.animalia.domain.User

data class ApiUser(
    var id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var lastLessonIndex: Int = 0,
    var lastQuestionIndex: Int = 0
)

fun ApiUser.asDomainModel(): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        lastLessonIndex = lastLessonIndex,
        lastQuestionIndex = lastQuestionIndex
    )
}
