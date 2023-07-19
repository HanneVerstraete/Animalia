package com.example.animalia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.animalia.databinding.FragmentTruefalseBinding

class TruefalseFragement : AppCompatActivity() {
    //    private val myLessonBook = LessonBook()
//
    private lateinit var binding: FragmentTruefalseBinding
//    var lessonNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_truefalse)

//        binding.lessonBook = myLessonBook

//        binding.apply {
//            nextlessonButton.setOnClickListener { nextLesson() }
//            quitlessonButton.setOnClickListener { quitLesson() }
//        }

    }

//    private fun nextLesson() {
//        binding.apply {
//            lessonBook?.getNextLesson(lessonNumber)
//
//            when (lessonNumber) {
//                0 -> animalImage.setImageResource(R.drawable.dog)
//                1 -> animalImage.setImageResource(R.drawable.lion)
//                2 -> animalImage.setImageResource(R.drawable.duck)
//                else -> animalImage.setImageResource(R.drawable.empty_vector)
//            }
//
//            invalidateAll()
//        }
//
//        lessonNumber += 1
//    }
//
//    private fun quitLesson() {
//        // TODO
//    }
}