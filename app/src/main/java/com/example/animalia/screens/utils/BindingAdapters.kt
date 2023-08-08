package com.example.animalia.screens.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.animalia.R
import com.example.animalia.database.lessons.DatabaseLesson
import com.example.animalia.database.users.User
import com.example.animalia.domain.Lesson

@BindingAdapter("lessonImage")
fun ImageView.setLessonImage(databaseLesson: DatabaseLesson?) {
    setImageResource(
        when (databaseLesson?.lessonId) {
            200L -> R.drawable.dog
            201L -> R.drawable.lion
            202L -> R.drawable.duck
            else -> R.drawable.empty_vector
        }
    )
}

@BindingAdapter("lessonCompletedImage")
fun ImageView.setLessonCompletedImage(lesson: Lesson?) {
    // TODO get actual user
    val user = User(301, 1)

    setImageResource(
        if (lesson?.index is Int && user.lastLessonIndex >= lesson.index) {
            R.drawable.star_gold
        } else {
            R.drawable.star_empty
        }
    )
}