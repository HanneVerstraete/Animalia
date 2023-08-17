package com.example.animalia.database.lessons

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animalia.domain.Lesson

@Entity(tableName = "lesson_table")
data class DatabaseLesson(
    @PrimaryKey
    @ColumnInfo(name = "lesson_id")
    var lessonId: String = "",

    @ColumnInfo(name = "lesson_index")
    var index: Int = 0,
    @ColumnInfo(name = "lesson_content")
    var content: String = "",
    @ColumnInfo(name = "lesson_title")
    var title: String = "",
    @ColumnInfo(name = "lesson_image_url")
    var imageUrl: String = "",
)

fun Array<DatabaseLesson>.asDomainModel(): Array<Lesson> {
    return map {
        Lesson(
            lessonId = it.lessonId,
            index = it.index,
            content = it.content,
            title = it.title,
            imageUrl = it.imageUrl
        )
    }.toTypedArray()
}

fun DatabaseLesson.asDomainModel(): Lesson {
    return Lesson(
        lessonId = lessonId,
        index = index,
        content = content,
        title = title,
        imageUrl = imageUrl
    )
}