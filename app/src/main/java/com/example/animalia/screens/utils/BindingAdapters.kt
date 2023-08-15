package com.example.animalia.screens.utils

import android.widget.ImageView
import android.widget.TextView
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

@BindingAdapter("levelValue")
fun TextView.setLevelValue(level: Int?) {
    text = resources.getString(R.string.level_value, level ?: 0)
}

@BindingAdapter("xpValue")
fun TextView.setXpValue(xp: Int?) {
    text = resources.getString(R.string.xp_value, xp ?: 0)
}

@BindingAdapter("finishedLessonsValue")
fun TextView.setFinishedLessonsValue(finishedLessons: Int?) {
    text = finishedLessons.toString()
}

@BindingAdapter("finishedQuizzesValue")
fun TextView.setFinishedQuizzesValue(finishedQuizzes: Int?) {
    if (finishedQuizzes != null) {
        text = (finishedQuizzes / 3).toString()
    }
}