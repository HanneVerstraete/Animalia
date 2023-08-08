package com.example.animalia.screens.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.animalia.R
import com.example.animalia.database.users.User
import com.example.animalia.domain.Lesson
import java.util.*

//@BindingAdapter("lessonImage")
//fun ImageView.setLessonImage(databaseLesson: DatabaseLesson?) {
//    setImageResource(
//        when (databaseLesson?.lessonId) {
//            200L -> R.drawable.dog
//            201L -> R.drawable.lion
//            202L -> R.drawable.duck
//            else -> R.drawable.empty_vector
//        }
//    )
//}

@BindingAdapter("lessonContent")
fun TextView.bindContent(lessons: Array<Lesson>?){
    lessons?.let{
        text = lessons[0].content
    }
}

@BindingAdapter("lessonTitle")
fun TextView.bindTitle(lessons: Array<Lesson>?){
    lessons?.let{
        text = lessons[0].title
    }
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