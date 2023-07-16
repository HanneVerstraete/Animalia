package com.example.animalia

class AnimalsLesson {
    companion object {
        private val lessons = listOf("A dog barks", "A lion roars", "A duck quacks")

        fun getNextLesson(lessonNumber: Int): String {
            val lesson = if (lessons.size > lessonNumber) {
                lessons.elementAt(lessonNumber)
            } else {
                "Finished all lessons"
            }
            return lesson
        }
    }
}
