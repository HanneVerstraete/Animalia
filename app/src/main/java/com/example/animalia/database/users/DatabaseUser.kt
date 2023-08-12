package com.example.animalia.database.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animalia.domain.User

@Entity(tableName = "user_table")
data class DatabaseUser(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    var userId: String = "",

    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    var lastName: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "user_finished_lessons")
    var lastLessonIndex: Int = 0,
    @ColumnInfo(name = "user_finished_questions")
    var lastQuestionIndex: Int = 0
)

fun DatabaseUser.asDomainModel(): User {
    return User(
        id = userId,
        firstName = firstName,
        lastName = lastName,
        email = email,
        lastLessonIndex = lastLessonIndex,
        lastQuestionIndex = lastQuestionIndex
    )
}