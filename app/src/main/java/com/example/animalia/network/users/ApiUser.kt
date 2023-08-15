package com.example.animalia.network.users

import com.example.animalia.database.users.DatabaseUser
import com.example.animalia.domain.User

data class ApiUser(
    var id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var lastLessonIndex: Int = 0,
    var lastQuestionIndex: Int = 0,
    var level: Int = 0,
    var xp: Int = 0
)

fun ApiUser.asDomainModel(): User {
    return User(
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

fun ApiUser.asDatabaseModel(): DatabaseUser {
    return DatabaseUser(
        userId = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        lastLessonIndex = lastLessonIndex,
        lastQuestionIndex = lastQuestionIndex,
        level = level,
        xp = xp
    )
}