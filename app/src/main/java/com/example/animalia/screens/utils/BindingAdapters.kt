package com.example.animalia.screens.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.animalia.R
import com.example.animalia.domain.Lesson
import com.example.animalia.sharedPreferences
import kotlinx.android.synthetic.main.lesson_overview_row_item.view.*
import java.util.*

@BindingAdapter("lessonImage")
fun setLessonImage(view: ImageView, lesson: Lesson?) {
    if (lesson != null) {
        Glide.with(view.context).load(lesson.imageUrl).into(view)
    }
}

@BindingAdapter("lessonCompletedImage")
fun ImageView.setLessonCompletedImage(lesson: Lesson?) {
    setImageResource(
        if (lesson?.index is Int && sharedPreferences.currentLesson > lesson.index) {
            R.drawable.ic_baseline_check_box_24
        } else {
            R.drawable.ic_baseline_check_box_outline_blank_24
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

@BindingAdapter("time")
fun TextView.setTime(time: Int) {
    var seconds = (time%60).toString()
    if (seconds.length == 1) {
        seconds = "0${seconds}"
    }
    var minutes = kotlin.math.floor(time.toDouble() / 60).toInt().toString()
    if (minutes.length == 1) {
        minutes = "0${minutes}"
    }
    text = resources.getString(R.string.time_text, minutes, seconds)
}