package com.example.animalia.screens.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.animalia.R
import com.example.animalia.domain.Lesson
import com.example.animalia.sharedPreferences
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
    setImageResource(
        if (lesson?.index is Int && sharedPreferences.currentLesson > lesson.index) {
            R.drawable.star_gold
        } else {
            R.drawable.star_empty
        }
    )
}