package com.example.animalia.domain

data class Lesson(
    var lessonId: Long = 0L,
    var index: Int = 0,
    var content: String = "",
    var title: String = ""
)