package com.example.animalia.database.lessons

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_table")
data class Lesson(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lesson_id")
    var lessonId: Long = 0L,

    @ColumnInfo(name = "lesson_index")
    var index: Int = 0,
    @ColumnInfo(name = "lesson_content")
    var content: String = "",
    @ColumnInfo(name = "lesson_title")
    var title: String = "",
)