package com.example.animalia.database.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var userId: Long = 0L,

    @ColumnInfo(name = "user_finished_lessons")
    var lastLessonIndex: Int = 0
)