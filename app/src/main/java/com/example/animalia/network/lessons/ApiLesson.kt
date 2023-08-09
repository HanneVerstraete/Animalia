package com.example.animalia.network.lessons

import com.example.animalia.database.lessons.DatabaseLesson
import com.example.animalia.domain.Lesson

data class ApiLesson(
    var id: String = "",
    var index: Int = 0,
    var content: String = "",
    var title: String = "",
)

fun Array<ApiLesson>.asDomainModel(): Array<Lesson>{
    return map{
        Lesson(
            index = it.index,
            content = it.content,
            title = it.title
        )
    }.toTypedArray()
}

fun Array<ApiLesson>.asDatabaseModel(): Array<DatabaseLesson>{
    return map{
        DatabaseLesson(
            lessonId = it.id,
            index = it.index,
            content = it.content,
            title = it.title
        )
    }.toTypedArray()
}

fun ApiLesson.asDatabaseLesson(): DatabaseLesson{
    return DatabaseLesson(
        lessonId = id,
        index = index,
        content = content,
        title = title
    )
}