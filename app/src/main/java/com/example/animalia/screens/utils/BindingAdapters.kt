package com.example.animalia.screens.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.animalia.R
import com.example.animalia.database.users.User
import com.example.animalia.domain.Lesson
import java.util.*

@BindingAdapter("lessonImage")
fun ImageView.setLessonImage(lesson: Lesson?) {
    setImageResource(
        when (lesson?.index) {
            0 -> R.drawable.dog
            1 -> R.drawable.lion
            2 -> R.drawable.duck
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