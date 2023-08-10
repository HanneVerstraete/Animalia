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
)

fun Array<DatabaseLesson>.asDomainModel(): Array<Lesson> {
    return map {
        Lesson(
            index = it.index,
            content = it.content,
            title = it.title
        )
    }.toTypedArray()
}

fun DatabaseLesson.asDomainModel(): Lesson {
    return Lesson(
        index = index,
        content = content,
        title = title
    )
}